package serratec.org.controller.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serratec.org.dto.ClienteAnalyticsDTO;
import serratec.org.dto.ProdutoAnalyticsDTO;
import serratec.org.service.RelatorioVendasService;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioVendasService relatorioVendasService;

    @GetMapping("/vendas-mensais")
    public ResponseEntity<Resource> gerarRelatorioVendasMensais(
            @RequestParam(required = true) Integer ano,
            @RequestParam(required = true) Integer mes) {
        try {
            ByteArrayInputStream bis = relatorioVendasService.gerarRelatorioVendasMensais(ano, mes);
            
            String filename = String.format("vendas_mensais_%d_%02d.xlsx", ano, mes);
            
            InputStreamResource file = new InputStreamResource(bis);
            
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/vendas/forma-pagamento")
    public ResponseEntity<Map<String, Object>> getVendasPorFormaPagamento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        Map<String, Object> relatorio = relatorioVendasService.gerarRelatorioVendasPorFormaPagamento(inicio, fim);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/clientes/analytics")
    public ResponseEntity<List<ClienteAnalyticsDTO>> getClientesAnalytics() {
        LocalDateTime inicio = LocalDateTime.now().minusMonths(6);
        LocalDateTime fim = LocalDateTime.now();
        
        List<ClienteAnalyticsDTO> analytics = relatorioVendasService.getClientesAnalytics(inicio, fim);
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/produtos/analytics")
    public ResponseEntity<List<ProdutoAnalyticsDTO>> getProdutosAnalytics() {
        LocalDateTime inicio = LocalDateTime.now().minusMonths(6);
        LocalDateTime fim = LocalDateTime.now();
        
        List<ProdutoAnalyticsDTO> analytics = relatorioVendasService.getProdutosAnalytics(inicio, fim);
        return ResponseEntity.ok(analytics);
    }
}