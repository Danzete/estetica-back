package serratec.org.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import serratec.org.dto.ClientDTO;
import serratec.org.entity.Client;
import serratec.org.exception.BusinessException;
import serratec.org.exception.ResourceNotFoundException;
import serratec.org.repository.ClientRepository;


@Service
@Transactional
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public Page<ClientDTO> findAll(String search, Pageable pageable) {
        Page<Client> clients;
        if (search != null && !search.trim().isEmpty()) {
            clients = clientRepository.findByNomeContainingIgnoreCaseOrClinicaContainingIgnoreCase(
                search, search, pageable);
        } else {
            clients = clientRepository.findAll(pageable);
        }
        return clients.map(this::convertToDTO);
    }
    
    public ClientDTO findById(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return convertToDTO(client);
    }
    
    public ClientDTO create(ClientDTO clientDTO) {
        validateClient(clientDTO);
        
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        
        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }
    
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        validateClientForUpdate(clientDTO, id);
        
        BeanUtils.copyProperties(clientDTO, existingClient, "id", "createdAt");
        
        Client updatedClient = clientRepository.save(existingClient);
        return convertToDTO(updatedClient);
    }
    
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        clientRepository.delete(client);
    }
    
    public List<ClientDTO> getTopClients() {
        List<Client> topClients = clientRepository.findTopClientsByTotalCompras(BigDecimal.ZERO);
        return topClients.stream()
            .limit(10)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // NOVOS MÉTODOS PARA RELATÓRIOS E ESTATÍSTICAS
    
    /**
     * Conta clientes ativos no último mês (usando parâmetro)
     */
    public Long getActiveClientsLastMonth() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return clientRepository.countActiveClientsLastMonth(thirtyDaysAgo);
    }
    
    /**
     * Conta clientes ativos no último mês (usando query nativa)
     */
    public Long getActiveClientsLastMonthAlt() {
        return clientRepository.countActiveClientsLastMonthAlt();
    }
    
    /**
     * Conta clientes ativos em um período personalizado
     */
    public Long getActiveClientsSince(LocalDate sinceDate) {
        return clientRepository.countActiveClientsLastMonth(sinceDate);
    }
    
    /**
     * Obtém estatísticas básicas dos clientes
     */
    public ClientStatsDTO getClientStats() {
        Long totalClients = clientRepository.count();
        Long activeLastMonth = getActiveClientsLastMonth();
        List<ClientDTO> topClients = getTopClients();
        
        return ClientStatsDTO.builder()
            .totalClients(totalClients)
            .activeClientsLastMonth(activeLastMonth)
            .topClientsCount(topClients.size())
            .build();
    }
    
    /**
     * Obtém clientes por faixa de valor de compras
     */
    public List<ClientDTO> getClientsByMinValue(BigDecimal minValue) {
        List<Client> clients = clientRepository.findTopClientsByTotalCompras(minValue);
        return clients.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private void validateClient(ClientDTO clientDTO) {
        if (clientDTO.getEmail() != null && !clientDTO.getEmail().trim().isEmpty()) {
            clientRepository.findByEmailIgnoreCase(clientDTO.getEmail())
                .ifPresent(client -> {
                    throw new BusinessException("Email já está em uso por outro cliente");
                });
        }
        
        if (clientDTO.getCpf() != null && !clientDTO.getCpf().trim().isEmpty()) {
            clientRepository.findByCpf(clientDTO.getCpf())
                .ifPresent(client -> {
                    throw new BusinessException("CPF já está em uso por outro cliente");
                });
        }
    }
    
    private void validateClientForUpdate(ClientDTO clientDTO, Long id) {
        if (clientDTO.getEmail() != null && !clientDTO.getEmail().trim().isEmpty()) {
            clientRepository.findByEmailIgnoreCase(clientDTO.getEmail())
                .ifPresent(client -> {
                    if (!client.getId().equals(id)) {
                        throw new BusinessException("Email já está em uso por outro cliente");
                    }
                });
        }
        
        if (clientDTO.getCpf() != null && !clientDTO.getCpf().trim().isEmpty()) {
            clientRepository.findByCpf(clientDTO.getCpf())
                .ifPresent(client -> {
                    if (!client.getId().equals(id)) {
                        throw new BusinessException("CPF já está em uso por outro cliente");
                    }
                });
        }
    }
    
    private ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        BeanUtils.copyProperties(client, dto);
        return dto;
    }
    
    // DTO interno para estatísticas
    public static class ClientStatsDTO {
        private Long totalClients;
        private Long activeClientsLastMonth;
        private Integer topClientsCount;
        
        // Construtor, getters e setters
        public ClientStatsDTO() {}
        
        public static ClientStatsBuilder builder() {
            return new ClientStatsBuilder();
        }
        
        public Long getTotalClients() { return totalClients; }
        public void setTotalClients(Long totalClients) { this.totalClients = totalClients; }
        
        public Long getActiveClientsLastMonth() { return activeClientsLastMonth; }
        public void setActiveClientsLastMonth(Long activeClientsLastMonth) { this.activeClientsLastMonth = activeClientsLastMonth; }
        
        public Integer getTopClientsCount() { return topClientsCount; }
        public void setTopClientsCount(Integer topClientsCount) { this.topClientsCount = topClientsCount; }
        
        public static class ClientStatsBuilder {
            private final ClientStatsDTO stats = new ClientStatsDTO();
            
            public ClientStatsBuilder totalClients(Long totalClients) {
                stats.setTotalClients(totalClients);
                return this;
            }
            
            public ClientStatsBuilder activeClientsLastMonth(Long activeClientsLastMonth) {
                stats.setActiveClientsLastMonth(activeClientsLastMonth);
                return this;
            }
            
            public ClientStatsBuilder topClientsCount(Integer topClientsCount) {
                stats.setTopClientsCount(topClientsCount);
                return this;
            }
            
            public ClientStatsDTO build() {
                return stats;
            }
        }
    }
}