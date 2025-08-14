package serratec.org.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {
    
    @Value("${app.file.upload-dir:./uploads/client-documents}")
    private String uploadDir;
    
    @Value("${app.file.base-url:http://localhost:8080}")
    private String baseUrl;
    
    @PostConstruct
    public void init() {
        try {
            // Criar diretório de upload na inicialização
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("Diretório de upload criado: " + uploadPath.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretório de upload!", e);
        }
    }
    
    /**
     * Salva arquivo e retorna informações sobre o arquivo
     */
    public FileInfo storeFile(MultipartFile file, Long clientId) throws IOException {
        // Validações
        if (!isValidImageFile(file)) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado");
        }
        
        if (!isValidFileSize(file, 5)) { // 5MB
            throw new IllegalArgumentException("Arquivo muito grande (máximo 5MB)");
        }
        
        // Gerar nome único para o arquivo
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = "client_" + clientId + "_" + UUID.randomUUID().toString() + extension;
        
        // Salvar arquivo
        Path uploadPath = Paths.get(uploadDir);
        Path targetLocation = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        // Retornar informações do arquivo
        return new FileInfo(
            uniqueFilename,
            "/api/files/client-documents/" + uniqueFilename, // URL relativa
            originalFilename,
            file.getContentType(),
            file.getSize()
        );
    }
    
    /**
     * Carrega arquivo do sistema de arquivos
     */
    public byte[] loadFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        
        if (!Files.exists(filePath)) {
            throw new IOException("Arquivo não encontrado: " + filename);
        }
        
        return Files.readAllBytes(filePath);
    }
    
    /**
     * Remove arquivo do sistema de arquivos
     */
    public boolean deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Erro ao deletar arquivo: " + filename + " - " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extrai nome do arquivo da URL
     */
    public String extractFilenameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        
        // Extrai o nome do arquivo da URL: /api/files/client-documents/filename.ext
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
    
    /**
     * Obtém a extensão do arquivo
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
    
    /**
     * Valida se é um arquivo de imagem
     */
    public boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        
        String contentType = file.getContentType();
        return contentType != null && (
            contentType.equals("image/jpeg") ||
            contentType.equals("image/jpg") ||
            contentType.equals("image/png") ||
            contentType.equals("image/gif") ||
            contentType.equals("image/webp") ||
            contentType.equals("application/pdf") // Aceitar PDF também
        );
    }
    
    /**
     * Valida tamanho do arquivo
     */
    public boolean isValidFileSize(MultipartFile file, long maxSizeInMB) {
        if (file == null) {
            return false;
        }
        return file.getSize() <= maxSizeInMB * 1024 * 1024;
    }
    
    /**
     * Obtém tipo de conteúdo baseado na extensão
     */
    public String getContentType(String filename) {
        if (filename == null) {
            return "application/octet-stream";
        }
        
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        
        switch (extension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "webp":
                return "image/webp";
            case "pdf":
                return "application/pdf";
            default:
                return "application/octet-stream";
        }
    }
    
    /**
     * Classe para informações do arquivo
     */
    public static class FileInfo {
        private final String filename;
        private final String url;
        private final String originalName;
        private final String contentType;
        private final long size;
        
        public FileInfo(String filename, String url, String originalName, String contentType, long size) {
            this.filename = filename;
            this.url = url;
            this.originalName = originalName;
            this.contentType = contentType;
            this.size = size;
        }
        
        public String getFilename() { return filename; }
        public String getUrl() { return url; }
        public String getOriginalName() { return originalName; }
        public String getContentType() { return contentType; }
        public long getSize() { return size; }
    }
}