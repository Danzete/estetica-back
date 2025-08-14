package serratec.org.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClientDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
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
    private BigDecimal totalCompras;
    private LocalDate ultimaCompra;
    
    // Campos para documento
    private String documentoUrl;        // URL para acessar o documento
    private String nomeArquivoOriginal; // Nome original do arquivo
    private String tipoArquivo;         // MIME type
    private Long tamanhoArquivo;        // Tamanho em bytes
    private Boolean temDocumento;       // Indica se possui documento
    
    // Constructors
    public ClientDTO() {}
    
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
    
    // Getters e Setters para documento
    public String getDocumentoUrl() { return documentoUrl; }
    public void setDocumentoUrl(String documentoUrl) { 
        this.documentoUrl = documentoUrl;
        this.temDocumento = documentoUrl != null && !documentoUrl.isEmpty();
    }
    
    public String getNomeArquivoOriginal() { return nomeArquivoOriginal; }
    public void setNomeArquivoOriginal(String nomeArquivoOriginal) { this.nomeArquivoOriginal = nomeArquivoOriginal; }
    
    public String getTipoArquivo() { return tipoArquivo; }
    public void setTipoArquivo(String tipoArquivo) { this.tipoArquivo = tipoArquivo; }
    
    public Long getTamanhoArquivo() { return tamanhoArquivo; }
    public void setTamanhoArquivo(Long tamanhoArquivo) { this.tamanhoArquivo = tamanhoArquivo; }
    
    public Boolean getTemDocumento() { return temDocumento; }
    public void setTemDocumento(Boolean temDocumento) { this.temDocumento = temDocumento; }
}