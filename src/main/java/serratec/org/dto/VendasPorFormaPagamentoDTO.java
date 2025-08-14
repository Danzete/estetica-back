package serratec.org.dto;


import java.math.BigDecimal;

import serratec.org.entity.FormaPagamento;


public class VendasPorFormaPagamentoDTO {
    private FormaPagamento formaPagamento;
    private Integer quantidade;
    private BigDecimal valor;
    private BigDecimal percentual;

    
    public VendasPorFormaPagamentoDTO(FormaPagamento formaPagamento, Integer quantidade, BigDecimal valor,
            BigDecimal percentual) {
        this.formaPagamento = formaPagamento;
        this.quantidade = quantidade;
        this.valor = valor;
        this.percentual = percentual;
    }
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public BigDecimal getPercentual() {
        return percentual;
    }
    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }
}