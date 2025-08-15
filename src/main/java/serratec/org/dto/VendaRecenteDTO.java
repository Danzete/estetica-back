package serratec.org.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import serratec.org.entity.FormaPagamento;



public class VendaRecenteDTO {
    private Long id;
    private String nomeCliente;
    private BigDecimal valorTotal;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataVenda;

    // Constructor matching the JPQL query
    public VendaRecenteDTO(Long id, String nomeCliente, BigDecimal valorTotal, FormaPagamento formaPagamento, LocalDateTime dataVenda) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.dataVenda = dataVenda;
    }

    // Default constructor
    public VendaRecenteDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }

    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }
}