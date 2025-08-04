package serratec.org.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.entity.SaleItem;


@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    
    List<SaleItem> findBySaleId(Long saleId);
    
    @Query("SELECT si FROM SaleItem si JOIN si.sale s WHERE s.dataVenda BETWEEN :startDate AND :endDate")
    List<SaleItem> findItemsByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT si.product.nome, SUM(si.quantidade) as totalQuantity FROM SaleItem si " +
           "JOIN si.sale s WHERE s.dataVenda BETWEEN :startDate AND :endDate " +
           "GROUP BY si.product.nome ORDER BY totalQuantity DESC")
    List<Object[]> findTopSellingProductsByPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}