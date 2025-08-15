package serratec.org.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {
    private BigDecimal totalVendasMes;
    private BigDecimal totalComissoesMes;
    private Integer quantidadeVendasMes;
    private List<ProdutoAnalyticsDTO> produtosMaisVendidos;
    private List<ProdutoAnalyticsDTO> produtosMenosVendidos;
    private List<ClienteAnalyticsDTO> clientesMaisCompraram;
    private List<VendaRecenteDTO> vendasRecentes;
    private List<VendasPorFormaPagamentoDTO> vendasPorFormaPagamento;
    
    // Getters e Setters
    public BigDecimal getTotalVendasMes() { return totalVendasMes; }
    public void setTotalVendasMes(BigDecimal totalVendasMes) { this.totalVendasMes = totalVendasMes; }
    
    public BigDecimal getTotalComissoesMes() { return totalComissoesMes; }
    public void setTotalComissoesMes(BigDecimal totalComissoesMes) { this.totalComissoesMes = totalComissoesMes; }
    
    public Integer getQuantidadeVendasMes() { return quantidadeVendasMes; }
    public void setQuantidadeVendasMes(Integer quantidadeVendasMes) { this.quantidadeVendasMes = quantidadeVendasMes; }
    
    public List<ProdutoAnalyticsDTO> getProdutosMaisVendidos() { return produtosMaisVendidos; }
    public void setProdutosMaisVendidos(List<ProdutoAnalyticsDTO> produtosMaisVendidos) { this.produtosMaisVendidos = produtosMaisVendidos; }
    
    public List<ProdutoAnalyticsDTO> getProdutosMenosVendidos() { return produtosMenosVendidos; }
    public void setProdutosMenosVendidos(List<ProdutoAnalyticsDTO> produtosMenosVendidos) { this.produtosMenosVendidos = produtosMenosVendidos; }
    
    public List<ClienteAnalyticsDTO> getClientesMaisCompraram() { return clientesMaisCompraram; }
    public void setClientesMaisCompraram(List<ClienteAnalyticsDTO> clientesMaisCompraram) { this.clientesMaisCompraram = clientesMaisCompraram; }
    
    public List<VendaRecenteDTO> getVendasRecentes() { return vendasRecentes; }
    public void setVendasRecentes(List<VendaRecenteDTO> vendasRecentes) { this.vendasRecentes = vendasRecentes; }
    
    public List<VendasPorFormaPagamentoDTO> getVendasPorFormaPagamento() { return vendasPorFormaPagamento; }
    public void setVendasPorFormaPagamento(List<VendasPorFormaPagamentoDTO> vendasPorFormaPagamento) { this.vendasPorFormaPagamento = vendasPorFormaPagamento; }
}