package serratec.org.repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.entity.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Page<Client> findByNomeContainingIgnoreCaseOrClinicaContainingIgnoreCase(
        String nome, String clinica, Pageable pageable);
    
    Optional<Client> findByEmailIgnoreCase(String email);
    
    Optional<Client> findByCpf(String cpf);
    
    @Query("SELECT c FROM Client c WHERE c.totalCompras > :minValue ORDER BY c.totalCompras DESC")
    List<Client> findTopClientsByTotalCompras(@Param("minValue") BigDecimal minValue);
    
    // Query original funcionando corretamente
    @Query("SELECT COUNT(c) FROM Client c WHERE c.ultimaCompra >= :thirtyDaysAgo")
    Long countActiveClientsLastMonth(@Param("thirtyDaysAgo") LocalDate thirtyDaysAgo);
    
    // OPÇÃO 1: Usando função SQL nativa (compatível com H2)
    @Query(value = "SELECT COUNT(*) FROM client WHERE ultima_compra >= DATEADD('DAY', -30, CURRENT_DATE)", nativeQuery = true)
    Long countActiveClientsLastMonthAlt();
}