package serratec.org.dto;

import java.math.BigDecimal;

public class ProdutoAnalyticsDTO {
    private String nomeProduto;
    private Long quantidadeVendida;
    private BigDecimal valorTotal;
    
    // Constructor needed for JPQL query
    public ProdutoAnalyticsDTO(String nomeProduto, Long quantidadeVendida, BigDecimal valorTotal) {
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
    }
    
    // Default constructor
    public ProdutoAnalyticsDTO() {}
    
    // Getters and Setters
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    
    public Long getQuantidadeVendida() { return quantidadeVendida; }
    public void setQuantidadeVendida(Long quantidadeVendida) { this.quantidadeVendida = quantidadeVendida; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}