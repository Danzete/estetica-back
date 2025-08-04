package serratec.org.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import serratec.org.repository.ClientRepository;
import serratec.org.repository.ProductRepository;
import serratec.org.repository.SaleRepository;



@Service
public class DashboardService {
    
    @Autowired
    private SaleRepository saleRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Vendas de hoje
        BigDecimal todaySales = saleRepository.getSalesAmountByDate(LocalDate.now());
        metrics.put("todaySales", todaySales != null ? todaySales : BigDecimal.ZERO);
        
        // Número de vendas hoje
        Long todaySalesCount = saleRepository.countTodaySales();
        metrics.put("todaySalesCount", todaySalesCount != null ? todaySalesCount : 0L);
        
        // Ticket médio dos últimos 30 dias
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        BigDecimal averageTicket = saleRepository.getAverageTicketByPeriod(thirtyDaysAgo, LocalDate.now());
        metrics.put("averageTicket", averageTicket != null ? averageTicket : BigDecimal.ZERO);
        
        // Clientes ativos no último mês - usando o método corrigido
        Long activeClients = clientRepository.countActiveClientsLastMonth(thirtyDaysAgo);
        metrics.put("activeClients", activeClients != null ? activeClients : 0L);
        
        // Produtos com estoque baixo
        Long lowStockProducts = productRepository.countLowStockProducts(10);
        metrics.put("lowStockProducts", lowStockProducts != null ? lowStockProducts : 0L);
        
        // Total de clientes
        Long totalClients = clientRepository.count();
        metrics.put("totalClients", totalClients);
        
        // Total de produtos
        Long totalProducts = productRepository.count();
        metrics.put("totalProducts", totalProducts);
        
        return metrics;
    }
}
