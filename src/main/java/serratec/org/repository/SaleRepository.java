package serratec.org.repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.entity.Sale;



@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
    Page<Sale> findByDataVendaBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    List<Sale> findByClientId(Long clientId);
    
    List<Sale> findByStatus(Sale.StatusVenda status);
    
    @Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.dataVenda = :date")
    BigDecimal getSalesAmountByDate(@Param("date") LocalDate date);
    
    @Query("SELECT s FROM Sale s WHERE s.dataVenda BETWEEN :startDate AND :endDate ORDER BY s.dataVenda DESC")
    List<Sale> findSalesByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(s) FROM Sale s WHERE s.dataVenda = CURRENT_DATE")
    Long countTodaySales();
    
    @Query("SELECT COALESCE(AVG(s.total), 0) FROM Sale s WHERE s.dataVenda BETWEEN :startDate AND :endDate")
    BigDecimal getAverageTicketByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}