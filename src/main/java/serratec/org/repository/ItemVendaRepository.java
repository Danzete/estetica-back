package serratec.org.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import serratec.org.entity.ItemVenda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    @Query("SELECT i.nomeProduto, SUM(i.quantidade) as total FROM ItemVenda i GROUP BY i.nomeProduto ORDER BY total DESC")
    List<Object[]> findProdutosMaisVendidos();
}