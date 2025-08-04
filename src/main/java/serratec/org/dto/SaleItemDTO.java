package serratec.org.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SaleItemDTO {
    
    private Long id;
    
    @NotNull(message = "Produto é obrigatório")
    private Long productId;
    
    private String productName;
    
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
    
    @DecimalMin(value = "0.0", message = "Preço unitário deve ser positivo")
    private BigDecimal precoUnitario;
    
    @DecimalMin(value = "0.0", message = "Subtotal deve ser positivo")
    private BigDecimal subtotal;
    
    // Constructors
    public SaleItemDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}