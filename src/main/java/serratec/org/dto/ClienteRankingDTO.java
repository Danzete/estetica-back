package serratec.org.dto;


import java.math.BigDecimal;


public class ClienteRankingDTO {
    private Long clienteId;
    private String nomeCliente;
    private Integer totalCompras;
    private BigDecimal valorTotal;
    private BigDecimal ticketMedio;
    public ClienteRankingDTO(Long clienteId, String nomeCliente, Integer totalCompras, BigDecimal valorTotal,
            BigDecimal ticketMedio) {
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.totalCompras = totalCompras;
        this.valorTotal = valorTotal;
        this.ticketMedio = ticketMedio;
    }
    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public Integer getTotalCompras() {
        return totalCompras;
    }
    public void setTotalCompras(Integer totalCompras) {
        this.totalCompras = totalCompras;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    public BigDecimal getTicketMedio() {
        return ticketMedio;
    }
    public void setTicketMedio(BigDecimal ticketMedio) {
        this.ticketMedio = ticketMedio;
    }
    
}
