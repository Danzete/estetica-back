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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clients")
public class Client extends AuditEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    @Column(nullable = false)
    private String nome;
    
    @Size(max = 100)
    private String clinica;
    
    @Size(max = 20)
    private String telefone;
    
    @Email(message = "Email deve ser válido")
    @Size(max = 100)
    private String email;
    
    @Size(max = 50)
    private String cidade;
    
    @Size(max = 200)
    private String endereco;
    
    @Size(max = 20)
    private String crm;
    
    @Size(max = 20)
    private String cro;
    
    @Size(max = 14)
    private String cpf;
    
    private LocalDate dataNascimento;
    
    @Column(name = "total_compras", precision = 10, scale = 2)
    private BigDecimal totalCompras = BigDecimal.ZERO;
    
    @Column(name = "ultima_compra")
    private LocalDate ultimaCompra;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sale> sales;
    
    // Constructors
    public Client() {}
    
    public Client(String nome, String clinica, String telefone, String email, String cidade) {
        this.nome = nome;
        this.clinica = clinica;
        this.telefone = telefone;
        this.email = email;
        this.cidade = cidade;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getClinica() { return clinica; }
    public void setClinica(String clinica) { this.clinica = clinica; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    
    public String getCro() { return cro; }
    public void setCro(String cro) { this.cro = cro; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public BigDecimal getTotalCompras() { return totalCompras; }
    public void setTotalCompras(BigDecimal totalCompras) { this.totalCompras = totalCompras; }
    
    public LocalDate getUltimaCompra() { return ultimaCompra; }
    public void setUltimaCompra(LocalDate ultimaCompra) { this.ultimaCompra = ultimaCompra; }
    
    public List<Sale> getSales() { return sales; }
    public void setSales(List<Sale> sales) { this.sales = sales; }
}