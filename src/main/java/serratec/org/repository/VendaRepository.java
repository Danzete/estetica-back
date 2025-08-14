package serratec.org.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.entity.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByClienteId(Long clienteId);
    
    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalVendasPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT SUM(v.totalComissao) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalComissaoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}