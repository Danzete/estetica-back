package serratec.org.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import serratec.org.entity.Cliente;
import serratec.org.entity.TipoCliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    List<Cliente> findByTipo(TipoCliente tipo);
}