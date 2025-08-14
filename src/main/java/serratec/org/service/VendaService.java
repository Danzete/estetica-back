package serratec.org.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import serratec.org.dto.DashboardDTO;
import serratec.org.dto.ItemVendaDTO;
import serratec.org.dto.VendaDTO;
import serratec.org.entity.Cliente;
import serratec.org.entity.ItemVenda;
import serratec.org.entity.Venda;
import serratec.org.repository.ItemVendaRepository;
import serratec.org.repository.VendaRepository;

@Service
@Transactional
public class VendaService {
    
    @Autowired
    private VendaRepository vendaRepository;
    
    @Autowired
    private ItemVendaRepository itemVendaRepository;
    
    @Autowired
    private ClienteService clienteService;
    

    
    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }
    
    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }
    
    public Venda criarVenda(VendaDTO vendaDTO) {
    Cliente cliente = clienteService.buscarPorId(vendaDTO.getClienteId())
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    
    Venda venda = new Venda();
    venda.setCliente(cliente);
     venda.setFrete(vendaDTO.getFrete());
    venda.setFormaPagamento(vendaDTO.getFormaPagamento());
    venda.setObservacoes(vendaDTO.getObservacoes());
    
    BigDecimal valorTotal = BigDecimal.ZERO;
    BigDecimal totalComissao = BigDecimal.ZERO;
    
    List<ItemVenda> itens = new ArrayList<>();
    
    for (ItemVendaDTO itemDTO : vendaDTO.getItens()) {
        ItemVenda item = new ItemVenda();
        item.setVenda(venda);
        item.setNomeProduto(itemDTO.getNomeProduto());
        item.setQuantidade(itemDTO.getQuantidade());
        item.setValorUnitario(itemDTO.getValorUnitario());
        
        BigDecimal valorTotalItem = itemDTO.getValorUnitario().multiply(new BigDecimal(itemDTO.getQuantidade()));
        item.setValorTotal(valorTotalItem);
        
        // Ajuste principal: usar apenas o percentual do DTO, sem referência à marca
        BigDecimal percentualComissao = itemDTO.getPercentualComissao() != null 
            ? itemDTO.getPercentualComissao() 
            : BigDecimal.ZERO; // Valor padrão quando não informado
        
        item.setPercentualComissao(percentualComissao);
        
        BigDecimal valorComissao = valorTotalItem.multiply(percentualComissao.divide(new BigDecimal(100)));
        item.setValorComissao(valorComissao);
        
        valorTotal = valorTotal.add(valorTotalItem);
        totalComissao = totalComissao.add(valorComissao);
        
        itens.add(item);
    }
    
   BigDecimal valorFrete = vendaDTO.getFrete() != null ? vendaDTO.getFrete() : BigDecimal.ZERO;
    venda.setValorTotal(valorTotal.add(valorFrete));
    venda.setTotalComissao(totalComissao);
    venda.setItens(itens);
    
    return vendaRepository.save(venda);

}
    public DashboardDTO gerarDashboard() {
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fimMes = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59);
        
        BigDecimal totalVendas = vendaRepository.calcularTotalVendasPeriodo(inicioMes, fimMes);
        BigDecimal totalComissoes = vendaRepository.calcularTotalComissaoPeriodo(inicioMes, fimMes);
        
        List<Venda> vendasRecentes = vendaRepository.findByDataVendaBetween(inicioMes, fimMes);
        
        DashboardDTO dashboard = new DashboardDTO();
        dashboard.setTotalVendas(totalVendas != null ? totalVendas : BigDecimal.ZERO);
        dashboard.setTotalComissoes(totalComissoes != null ? totalComissoes : BigDecimal.ZERO);
        dashboard.setQuantidadeVendas(vendasRecentes.size());
        dashboard.setVendasRecentes(vendasRecentes);
        
        return dashboard;
    }
}