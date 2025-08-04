package serratec.org.controller.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import serratec.org.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Métricas do dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping("/metrics")
    @Operation(summary = "Buscar métricas do dashboard")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        Map<String, Object> metrics = dashboardService.getDashboardMetrics();
        return ResponseEntity.ok(metrics);
    }
}