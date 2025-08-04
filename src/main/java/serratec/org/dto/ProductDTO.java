package serratec.org.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para transferência de dados de produtos
 * Usado para comunicação entre as camadas da aplicação
 */
public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @Size(max = 50, message = "Categoria deve ter no máximo 50 caracteres")
    private String categoria;
    
    @Size(max = 50, message = "Fabricante deve ter no máximo 50 caracteres")
    private String fabricante;
    
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque = 0;
    
    @DecimalMin(value = "0.0", message = "Preço de compra deve ser positivo")
    private BigDecimal precoCompra;
    
    @DecimalMin(value = "0.0", message = "Preço PIX deve ser positivo")
    private BigDecimal precoPix;
    
    @DecimalMin(value = "0.0", message = "Preço cartão deve ser positivo")
    private BigDecimal precoCartao;
    
    @Size(max = 50, message = "Fornecedor deve ter no máximo 50 caracteres")
    private String fornecedor;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validade;
    
    @Size(max = 20, message = "Código de barras deve ter no máximo 20 caracteres")
    private String codigoBarras;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;
    
    // Campos de auditoria
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    // Campos calculados/derivados
    private Boolean estoquebaixo;
    private Integer diasParaVencimento;
    private BigDecimal margemLucro;
    
    // Constructors
    public ProductDTO() {}
    
    public ProductDTO(String nome, String categoria, String fabricante, Integer estoque, 
                     BigDecimal precoPix, BigDecimal precoCartao) {
        this.nome = nome;
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.estoque = estoque;
        this.precoPix = precoPix;
        this.precoCartao = precoCartao;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getFabricante() {
        return fabricante;
    }
    
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    
    public Integer getEstoque() {
        return estoque;
    }
    
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
        // Calcula automaticamente se o estoque está baixo
        this.estoquebaixo = estoque != null && estoque < 10;
    }
    
    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }
    
    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
        calcularMargemLucro();
    }
    
    public BigDecimal getPrecoPix() {
        return precoPix;
    }
    
    public void setPrecoPix(BigDecimal precoPix) {
        this.precoPix = precoPix;
        calcularMargemLucro();
    }
    
    public BigDecimal getPrecoCartao() {
        return precoCartao;
    }
    
    public void setPrecoCartao(BigDecimal precoCartao) {
        this.precoCartao = precoCartao;
    }
    
    public String getFornecedor() {
        return fornecedor;
    }
    
    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public LocalDate getValidade() {
        return validade;
    }
    
    public void setValidade(LocalDate validade) {
        this.validade = validade;
        calcularDiasParaVencimento();
    }
    
    public String getCodigoBarras() {
        return codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getEstoqueBaixo() {
        return estoquebaixo;
    }
    
    public void setEstoqueBaixo(Boolean estoqueBaixo) {
        this.estoquebaixo = estoqueBaixo;
    }
    
    public Integer getDiasParaVencimento() {
        return diasParaVencimento;
    }
    
    public void setDiasParaVencimento(Integer diasParaVencimento) {
        this.diasParaVencimento = diasParaVencimento;
    }
    
    public BigDecimal getMargemLucro() {
        return margemLucro;
    }
    
    public void setMargemLucro(BigDecimal margemLucro) {
        this.margemLucro = margemLucro;
    }
    
    // Métodos auxiliares
    private void calcularDiasParaVencimento() {
        if (this.validade != null) {
            this.diasParaVencimento = (int) java.time.temporal.ChronoUnit.DAYS.between(
                LocalDate.now(), this.validade
            );
        }
    }
    
    private void calcularMargemLucro() {
        if (this.precoCompra != null && this.precoPix != null && 
            this.precoCompra.compareTo(BigDecimal.ZERO) > 0) {
            
            BigDecimal lucro = this.precoPix.subtract(this.precoCompra);
            this.margemLucro = lucro.divide(this.precoCompra, 4, java.math.RoundingMode.HALF_UP)
                              .multiply(new BigDecimal("100"));
        }
    }
    
    // Métodos de conveniência
    public boolean isProdutoVencido() {
        return this.validade != null && this.validade.isBefore(LocalDate.now());
    }
    
    public boolean isProdutoProximoVencimento() {
        return this.diasParaVencimento != null && this.diasParaVencimento <= 30 && this.diasParaVencimento > 0;
    }
    
    public boolean isEstoqueDisponivel() {
        return this.estoque != null && this.estoque > 0;
    }
    
    public BigDecimal getPrecoByFormaPagamento(String formaPagamento) {
        if ("PIX".equalsIgnoreCase(formaPagamento)) {
            return this.precoPix;
        } else if ("CARTAO".equalsIgnoreCase(formaPagamento)) {
            return this.precoCartao;
        }
        return this.precoPix; // Default para PIX
    }
    
    // toString, equals e hashCode
    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", estoque=" + estoque +
                ", precoPix=" + precoPix +
                ", precoCartao=" + precoCartao +
                ", validade=" + validade +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProductDTO that = (ProductDTO) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}