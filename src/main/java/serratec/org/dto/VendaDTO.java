package serratec.org.dto;

import java.math.BigDecimal;
import java.util.List;

import serratec.org.entity.FormaPagamento;

public class VendaDTO {
    private Long clienteId;
    private List<ItemVendaDTO> itens;
    private FormaPagamento formaPagamento;
    private String observacoes;
    private BigDecimal frete;
    
    // Getters e Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public List<ItemVendaDTO> getItens() { return itens; }
    public void setItens(List<ItemVendaDTO> itens) { this.itens = itens; }
    
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }
}