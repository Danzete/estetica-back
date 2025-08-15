package serratec.org.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serratec.org.dto.ClienteAnalyticsDTO;
import serratec.org.dto.ProdutoAnalyticsDTO;
import serratec.org.entity.FormaPagamento;
import serratec.org.entity.ItemVenda;
import serratec.org.entity.Venda;
import serratec.org.repository.VendaRepository;

@Service
public class RelatorioVendasService {
    
    @Autowired
    private VendaRepository vendaRepository;

    public ByteArrayInputStream gerarRelatorioVendasMensais(int ano, int mes) {
        LocalDateTime inicio = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime fim = inicio.plusMonths(1).minusSeconds(1);

        List<Venda> vendas = vendaRepository.findByDataVendaBetween(inicio, fim);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vendas Mensais");

            // Estilos
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            // Cabeçalho
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "ID Venda", "Data Venda", "Cliente", "CPF/CNPJ", 
                "Valor Total", "Frete", "Total Comissão", 
                "Forma Pagamento", "Observações", "Status",
                "Itens (Produto - Quantidade - Valor Unitário - Valor Total - % Comissão - Valor Comissão)"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Dados
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Venda venda : vendas) {
                Row row = sheet.createRow(rowNum++);

                // Dados básicos da venda
                row.createCell(0).setCellValue(venda.getId());
                row.createCell(1).setCellValue(venda.getDataVenda().format(formatter));
                row.createCell(2).setCellValue(venda.getCliente().getNome());
                row.createCell(3).setCellValue(
                    venda.getCliente().getCpf() != null ? venda.getCliente().getCpf() : 
                    venda.getCliente().getCnpj() != null ? venda.getCliente().getCnpj() : ""
                );
                row.createCell(4).setCellValue(venda.getValorTotal().doubleValue());
                row.createCell(5).setCellValue(venda.getFrete() != null ? venda.getFrete().doubleValue() : 0);
                row.createCell(6).setCellValue(venda.getTotalComissao().doubleValue());
                row.createCell(7).setCellValue(venda.getFormaPagamento().toString());
                row.createCell(8).setCellValue(venda.getObservacoes() != null ? venda.getObservacoes() : "");
                row.createCell(9).setCellValue(venda.getStatus().toString());

                // Itens da venda (um por linha)
                StringBuilder itensStr = new StringBuilder();
                for (ItemVenda item : venda.getItens()) {
                    itensStr.append(String.format(
                        "%s - %d - R$ %.2f - R$ %.2f - %.2f%% - R$ %.2f; ",
                        item.getNomeProduto(),
                        item.getQuantidade(),
                        item.getValorUnitario(),
                        item.getValorTotal(),
                        item.getPercentualComissao(),
                        item.getValorComissao()
                    ));
                }
                row.createCell(10).setCellValue(itensStr.toString());

                // Aplicar estilo às células
                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar relatório Excel: " + e.getMessage());
        }
    }

    public Map<String, Object> gerarRelatorioVendasPorFormaPagamento(LocalDateTime inicio, LocalDateTime fim) {
        List<Venda> vendas = vendaRepository.findByDataVendaBetween(inicio, fim);
        
        Map<FormaPagamento, Long> quantidadePorFormaPagamento = vendas.stream()
            .collect(Collectors.groupingBy(Venda::getFormaPagamento, Collectors.counting()));
            
        Map<FormaPagamento, BigDecimal> valorPorFormaPagamento = vendas.stream()
            .collect(Collectors.groupingBy(
                Venda::getFormaPagamento,
                Collectors.reducing(BigDecimal.ZERO, Venda::getValorTotal, BigDecimal::add)
            ));

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("quantidadePorFormaPagamento", quantidadePorFormaPagamento);
        resultado.put("valorPorFormaPagamento", valorPorFormaPagamento);
        
        return resultado;
    }

    public List<ClienteAnalyticsDTO> getClientesAnalytics(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findClientesMaisCompraram(inicio, fim);
    }

    public List<ProdutoAnalyticsDTO> getProdutosAnalytics(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.findProdutosMaisVendidos(inicio, fim);
    }
}