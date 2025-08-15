package serratec.org.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import serratec.org.dto.ClienteAnalyticsDTO;
import serratec.org.dto.ProdutoAnalyticsDTO;
import serratec.org.dto.VendaRecenteDTO;
import serratec.org.entity.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Venda> findByClienteId(Long clienteId);
    
    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalVendasPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT SUM(v.totalComissao) FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalComissaoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim")
    List<Venda> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("""
        SELECT new serratec.org.dto.ProdutoAnalyticsDTO(
            i.nomeProduto, 
            CAST(SUM(i.quantidade) AS long), 
            SUM(i.valorTotal)
        )
        FROM ItemVenda i
        WHERE i.venda.dataVenda BETWEEN :inicio AND :fim
        GROUP BY i.nomeProduto
        ORDER BY SUM(i.quantidade) DESC
    """)
    List<ProdutoAnalyticsDTO> findProdutosMaisVendidos(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("""
        SELECT new serratec.org.dto.ClienteAnalyticsDTO(
            v.cliente.nome,
            CAST(COUNT(v) AS long),
            SUM(v.valorTotal)
        )
        FROM Venda v
        WHERE v.dataVenda BETWEEN :inicio AND :fim
        GROUP BY v.cliente.nome
        ORDER BY COUNT(v) DESC
    """)
    List<ClienteAnalyticsDTO> findClientesMaisCompraram(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("""
        SELECT new serratec.org.dto.VendaRecenteDTO(
            v.id,
            v.cliente.nome,
            v.valorTotal,
            v.formaPagamento,
            v.dataVenda
        )
        FROM Venda v
        ORDER BY v.dataVenda DESC
    """)
    List<VendaRecenteDTO> findVendasRecentes();
}