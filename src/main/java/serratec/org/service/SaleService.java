package serratec.org.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import serratec.org.dto.SaleDTO;
import serratec.org.dto.SaleItemDTO;
import serratec.org.entity.Client;
import serratec.org.entity.Product;
import serratec.org.entity.Sale;
import serratec.org.entity.SaleItem;
import serratec.org.exception.BusinessException;
import serratec.org.exception.ResourceNotFoundException;
import serratec.org.repository.ClientRepository;
import serratec.org.repository.ProductRepository;
import serratec.org.repository.SaleRepository;



@Service
@Transactional
public class SaleService {
    
    @Autowired
    private SaleRepository saleRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public Page<SaleDTO> findAll(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Sale> sales;
        if (startDate != null && endDate != null) {
            sales = saleRepository.findByDataVendaBetween(startDate, endDate, pageable);
        } else {
            sales = saleRepository.findAll(pageable);
        }
        return sales.map(this::convertToDTO);
    }
    
    public SaleDTO findById(Long id) {
        Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com ID: " + id));
        return convertToDTO(sale);
    }
    
    public SaleDTO create(SaleDTO saleDTO) {
        // Validar cliente
        Client client = clientRepository.findById(saleDTO.getClientId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        
        // Criar venda
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setDataVenda(saleDTO.getDataVenda() != null ? saleDTO.getDataVenda() : LocalDate.now());
        sale.setFormaPagamento(saleDTO.getFormaPagamento());
        sale.setDesconto(saleDTO.getDesconto() != null ? saleDTO.getDesconto() : BigDecimal.ZERO);
        sale.setObservacoes(saleDTO.getObservacoes());
        sale.setStatus(Sale.StatusVenda.PENDENTE);
        
        // Inicializar lista de itens
        sale.setItems(new ArrayList<>());
        
        // Processar itens
        BigDecimal subtotal = BigDecimal.ZERO;
        if (saleDTO.getItems() != null) {
            for (SaleItemDTO itemDTO : saleDTO.getItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
                
                // Verificar estoque
                if (product.getEstoque() < itemDTO.getQuantidade()) {
                    throw new BusinessException(
                        String.format("Estoque insuficiente para o produto %s. Disponível: %d", 
                        product.getNome(), product.getEstoque())
                    );
                }
                
                // Criar item da venda
                SaleItem saleItem = new SaleItem();
                saleItem.setSale(sale);
                saleItem.setProduct(product);
                saleItem.setQuantidade(itemDTO.getQuantidade());
                saleItem.setPrecoUnitario(itemDTO.getPrecoUnitario());
                saleItem.setSubtotal(itemDTO.getPrecoUnitario().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));
                
                sale.getItems().add(saleItem);
                subtotal = subtotal.add(saleItem.getSubtotal());
                
                // Atualizar estoque
                product.setEstoque(product.getEstoque() - itemDTO.getQuantidade());
                productRepository.save(product);
            }
        }
        
        sale.setSubtotal(subtotal);
        sale.setTotal(subtotal.subtract(sale.getDesconto()));
        
        Sale savedSale = saleRepository.save(sale);
        
        // Atualizar dados do cliente
        updateClientData(client);
        
        return convertToDTO(savedSale);
    }
    
    public SaleDTO updateStatus(Long id, Sale.StatusVenda status) {
        Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com ID: " + id));
        
        sale.setStatus(status);
        Sale updatedSale = saleRepository.save(sale);
        
        return convertToDTO(updatedSale);
    }
    
    public void delete(Long id) {
        Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com ID: " + id));
        
        // Reverter estoque
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                Product product = item.getProduct();
                product.setEstoque(product.getEstoque() + item.getQuantidade());
                productRepository.save(product);
            }
        }
        
        saleRepository.delete(sale);
        
        // Atualizar dados do cliente
        updateClientData(sale.getClient());
    }
    
    public List<SaleDTO> findByClient(Long clientId) {
        List<Sale> sales = saleRepository.findByClientId(clientId);
        return sales.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private void updateClientData(Client client) {
        List<Sale> clientSales = saleRepository.findByClientId(client.getId());
        
        BigDecimal totalCompras = clientSales.stream()
            .filter(sale -> sale.getStatus() == Sale.StatusVenda.ENTREGUE)
            .map(Sale::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        LocalDate ultimaCompra = clientSales.stream()
            .filter(sale -> sale.getStatus() == Sale.StatusVenda.ENTREGUE)
            .map(Sale::getDataVenda)
            .max(LocalDate::compareTo)
            .orElse(null);
        
        client.setTotalCompras(totalCompras);
        client.setUltimaCompra(ultimaCompra);
        clientRepository.save(client);
    }
    
    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        BeanUtils.copyProperties(sale, dto);
        dto.setClientId(sale.getClient().getId());
        dto.setClientName(sale.getClient().getNome());
        dto.setClinicName(sale.getClient().getClinica());
        
        if (sale.getItems() != null) {
            List<SaleItemDTO> itemDTOs = sale.getItems().stream()
                .map(this::convertItemToDTO)
                .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }
        
        return dto;
    }
    
    private SaleItemDTO convertItemToDTO(SaleItem item) {
        SaleItemDTO dto = new SaleItemDTO();
        BeanUtils.copyProperties(item, dto);
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getNome());
        return dto;
    }
}