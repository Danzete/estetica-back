package serratec.org.dto;

import java.math.BigDecimal;

public class ClienteAnalyticsDTO {
    private String nomeCliente;
    private Long quantidadeCompras;
    private BigDecimal valorTotal;
    
    // Construtor necessário para a query JPQL
    public ClienteAnalyticsDTO(String nomeCliente, Long quantidadeCompras, BigDecimal valorTotal) {
        this.nomeCliente = nomeCliente;
        this.quantidadeCompras = quantidadeCompras;
        this.valorTotal = valorTotal;
    }
    
    // Construtor padrão
    public ClienteAnalyticsDTO() {}
    
    // Getters e Setters
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    
    public Long getQuantidadeCompras() { return quantidadeCompras; }
    public void setQuantidadeCompras(Long quantidadeCompras) { this.quantidadeCompras = quantidadeCompras; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}