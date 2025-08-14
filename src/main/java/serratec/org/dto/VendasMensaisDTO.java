package serratec.org.dto;


import java.math.BigDecimal;


public class VendasMensaisDTO {
    private String mes;
    private Integer ano;
    private BigDecimal valor;
    private Integer quantidade;
    public VendasMensaisDTO(String mes, Integer ano, BigDecimal valor, Integer quantidade) {
        this.mes = mes;
        this.ano = ano;
        this.valor = valor;
        this.quantidade = quantidade;
    }
    public String getMes() {
        return mes;
    }
    public void setMes(String mes) {
        this.mes = mes;
    }
    public Integer getAno() {
        return ano;
    }
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}