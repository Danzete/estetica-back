package serratec.org.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import serratec.org.entity.Cliente;
import serratec.org.entity.TipoCliente;
import serratec.org.repository.ClienteRepository;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public List<Cliente> listarPorTipo(TipoCliente tipo) {
        return clienteRepository.findByTipo(tipo);
    }
}