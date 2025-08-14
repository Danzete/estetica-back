package serratec.org.dto;

import java.math.BigDecimal;
import java.util.List;

import serratec.org.entity.Venda;

public class DashboardDTO {
    private BigDecimal totalVendas;
    private BigDecimal totalComissoes;
    private Integer quantidadeVendas;
    private List<Venda> vendasRecentes;
    
    // Getters e Setters
    public BigDecimal getTotalVendas() { return totalVendas; }
    public void setTotalVendas(BigDecimal totalVendas) { this.totalVendas = totalVendas; }
    
    public BigDecimal getTotalComissoes() { return totalComissoes; }
    public void setTotalComissoes(BigDecimal totalComissoes) { this.totalComissoes = totalComissoes; }
    
    public Integer getQuantidadeVendas() { return quantidadeVendas; }
    public void setQuantidadeVendas(Integer quantidadeVendas) { this.quantidadeVendas = quantidadeVendas; }
    
    public List<Venda> getVendasRecentes() { return vendasRecentes; }
    public void setVendasRecentes(List<Venda> vendasRecentes) { this.vendasRecentes = vendasRecentes; }
}