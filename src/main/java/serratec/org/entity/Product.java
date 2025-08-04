package serratec.org.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product extends AuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 50)
    private String categoria;
    
    @Size(max = 50)
    private String fabricante;
    
    @Min(value = 0, message = "Estoque não pode ser negativo")
    @Column(nullable = false)
    private Integer estoque = 0;
    
    @DecimalMin(value = "0.0", message = "Preço de compra deve ser positivo")
    @Column(name = "preco_compra", precision = 10, scale = 2)
    private BigDecimal precoCompra;
    
    @DecimalMin(value = "0.0", message = "Preço PIX deve ser positivo")
    @Column(name = "preco_pix", precision = 10, scale = 2)
    private BigDecimal precoPix;
    
    @DecimalMin(value = "0.0", message = "Preço cartão deve ser positivo")
    @Column(name = "preco_cartao", precision = 10, scale = 2)
    private BigDecimal precoCartao;
    
    @Size(max = 50)
    private String fornecedor;
    
    private LocalDate validade;
    
    @Size(max = 20)
    @Column(name = "codigo_barras")
    private String codigoBarras;
    
    @Size(max = 500)
    private String descricao;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleItem> saleItems;
    
    // Constructors
    public Product() {}
    
    public Product(String nome, String categoria, String fabricante, Integer estoque, 
                   BigDecimal precoPix, BigDecimal precoCartao) {
        this.nome = nome;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.estoque = estoque;
        this.precoPix = precoPix;
        this.precoCartao = precoCartao;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    
    public BigDecimal getPrecoCompra() { return precoCompra; }
    public void setPrecoCompra(BigDecimal precoCompra) { this.precoCompra = precoCompra; }
    
    public BigDecimal getPrecoPix() { return precoPix; }
    public void setPrecoPix(BigDecimal precoPix) { this.precoPix = precoPix; }
    
    public BigDecimal getPrecoCartao() { return precoCartao; }
    public void setPrecoCartao(BigDecimal precoCartao) { this.precoCartao = precoCartao; }
    
    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }
    
    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }
    
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}