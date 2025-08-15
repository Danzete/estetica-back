package serratec.org.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serratec.org.dto.DashboardDTO;
import serratec.org.dto.ProdutoAnalyticsDTO;
import serratec.org.entity.Venda;
import serratec.org.repository.VendaRepository;

@Service
public class DashboardService {
    
    @Autowired
    private VendaRepository vendaRepository;
    
    public DashboardDTO getDashboardData() {
        LocalDateTime inicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fim = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        
        DashboardDTO dashboard = new DashboardDTO();
        
        // Vendas do mês
        List<Venda> vendasMes = vendaRepository.findByPeriodo(inicio, fim);
        dashboard.setTotalVendasMes(vendasMes.stream()
            .map(Venda::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        dashboard.setQuantidadeVendasMes(vendasMes.size());
        
        // Produtos mais e menos vendidos
        List<ProdutoAnalyticsDTO> produtosAnalytics = vendaRepository.findProdutosMaisVendidos(inicio, fim);
        dashboard.setProdutosMaisVendidos(produtosAnalytics.subList(0, Math.min(5, produtosAnalytics.size())));
        dashboard.setProdutosMenosVendidos(produtosAnalytics.subList(Math.max(0, produtosAnalytics.size() - 5), produtosAnalytics.size()));
        
        // Clientes que mais compraram
        dashboard.setClientesMaisCompraram(vendaRepository.findClientesMaisCompraram(inicio, fim)
            .subList(0, Math.min(5, produtosAnalytics.size())));
            
        // Vendas recentes
        dashboard.setVendasRecentes(vendaRepository.findVendasRecentes());
        
        return dashboard;
    }
}