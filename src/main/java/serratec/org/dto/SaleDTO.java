package serratec.org.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import serratec.org.entity.Sale;

public class SaleDTO {
    
    private Long id;
    
    @NotNull(message = "Cliente é obrigatório")
    private Long clientId;
    
    private String clientName;
    private String clinicName;
    
    private LocalDate dataVenda;
    
    @DecimalMin(value = "0.0", message = "Subtotal deve ser positivo")
    private BigDecimal subtotal;
    
    @DecimalMin(value = "0.0", message = "Desconto deve ser positivo")
    private BigDecimal desconto;
    
    @DecimalMin(value = "0.0", message = "Total deve ser positivo")
    private BigDecimal total;
    
    private Sale.FormaPagamento formaPagamento;
    private Sale.StatusVenda status;
    private String observacoes;
    
    private List<SaleItemDTO> items;
    
    // Constructors
    public SaleDTO() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
    
    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public Sale.FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(Sale.FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    
    public Sale.StatusVenda getStatus() { return status; }
    public void setStatus(Sale.StatusVenda status) { this.status = status; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public List<SaleItemDTO> getItems() { return items; }
    public void setItems(List<SaleItemDTO> items) { this.items = items; }
}