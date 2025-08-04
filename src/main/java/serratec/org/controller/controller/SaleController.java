package serratec.org.controller.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import serratec.org.dto.SaleDTO;
import serratec.org.entity.Sale;
import serratec.org.service.SaleService;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Vendas", description = "Gerenciamento de vendas")
@CrossOrigin(origins = "*")
public class SaleController {
    
    @Autowired
    private SaleService saleService;
    
    @GetMapping
    @Operation(summary = "Listar todas as vendas")
    public ResponseEntity<Page<SaleDTO>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<SaleDTO> sales = saleService.findAll(startDate, endDate, pageable);
        return ResponseEntity.ok(sales);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID")
    public ResponseEntity<SaleDTO> findById(@PathVariable Long id) {
        SaleDTO sale = saleService.findById(id);
        return ResponseEntity.ok(sale);
    }
    
    @PostMapping
    @Operation(summary = "Criar nova venda")
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO saleDTO) {
        SaleDTO createdSale = saleService.create(saleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSale);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status da venda")
    public ResponseEntity<SaleDTO> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Sale.StatusVenda status = Sale.StatusVenda.valueOf(request.get("status"));
        SaleDTO updatedSale = saleService.updateStatus(id, status);
        return ResponseEntity.ok(updatedSale);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir venda")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Buscar vendas por cliente")
    public ResponseEntity<List<SaleDTO>> findByClient(@PathVariable Long clientId) {
        List<SaleDTO> sales = saleService.findByClient(clientId);
        return ResponseEntity.ok(sales);
    }
}