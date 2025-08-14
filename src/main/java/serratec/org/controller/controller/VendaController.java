package serratec.org.controller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import serratec.org.dto.DashboardDTO;
import serratec.org.dto.VendaDTO;
import serratec.org.entity.Venda;
import serratec.org.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "*")
public class VendaController {
    
    @Autowired
    private VendaService vendaService;
    
    @GetMapping
    public ResponseEntity<List<Venda>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }
    
    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaDTO vendaDTO) {
        try {
            Venda novaVenda = vendaService.criarVenda(vendaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        return ResponseEntity.ok(vendaService.gerarDashboard());
    }
}