package serratec.org.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.entity.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByNomeContainingIgnoreCaseOrCategoriaContainingIgnoreCase(
        String nome, String categoria, org.springframework.data.domain.Pageable pageable);
    
    List<Product> findByEstoqueLessThan(Integer minStock);
    
    List<Product> findByValidadeBefore(LocalDate date);
    
    List<Product> findByCategoria(String categoria);
    
    // Corrigindo a query para LEFT JOIN
    @Query("SELECT p FROM Product p LEFT JOIN p.saleItems si GROUP BY p.id ORDER BY COALESCE(SUM(si.quantidade), 0) DESC")
    List<Product> findTopSellingProducts(PageRequest pageable);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.estoque < :threshold")
    Long countLowStockProducts(@Param("threshold") Integer threshold);
}