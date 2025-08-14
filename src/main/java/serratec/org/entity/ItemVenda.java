package serratec.org.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_venda")
public class ItemVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonBackReference
    private Venda venda;
    
   
    
    @Column(nullable = false)
    private String nomeProduto;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(nullable = false)
    private BigDecimal valorUnitario;
    
    @Column(nullable = false)
    private BigDecimal valorTotal;
    
    @Column(nullable = false)
    private BigDecimal percentualComissao;
    
    @Column(nullable = false)
    private BigDecimal valorComissao;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }
    
  
    
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }
    
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    
    public BigDecimal getPercentualComissao() { return percentualComissao; }
    public void setPercentualComissao(BigDecimal percentualComissao) { this.percentualComissao = percentualComissao; }
    
    public BigDecimal getValorComissao() { return valorComissao; }
    public void setValorComissao(BigDecimal valorComissao) { this.valorComissao = valorComissao; }
}