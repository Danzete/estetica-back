package serratec.org.controller.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import serratec.org.dto.ClientDTO;
import serratec.org.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<ClientDTO> clients = clientService.findAll(search, pageable);
        return ResponseEntity.ok(clients);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }
    
    @PostMapping
    @Operation(summary = "Criar novo cliente")
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.create(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.update(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/top")
    @Operation(summary = "Buscar top clientes")
    public ResponseEntity<List<ClientDTO>> getTopClients() {
        List<ClientDTO> topClients = clientService.getTopClients();
        return ResponseEntity.ok(topClients);
    }
        @GetMapping("/stats")
    public ResponseEntity<ClientService.ClientStatsDTO> getStats() {
        return ResponseEntity.ok(clientService.getClientStats());
    }
    
    // Endpoint para clientes ativos
    @GetMapping("/active-last-month")
    public ResponseEntity<Long> getActiveLastMonth() {
        return ResponseEntity.ok(clientService.getActiveClientsLastMonth());
    }
    
    // Endpoint para período personalizado
    @GetMapping("/active-since/{date}")
    public ResponseEntity<Long> getActiveSince(@PathVariable LocalDate date) {
        return ResponseEntity.ok(clientService.getActiveClientsSince(date));
    }

}