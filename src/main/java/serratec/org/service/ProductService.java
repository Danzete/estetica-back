package serratec.org.service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import serratec.org.dto.ProductDTO;
import serratec.org.entity.Product;
import serratec.org.exception.BusinessException;
import serratec.org.exception.ResourceNotFoundException;
import serratec.org.repository.ProductRepository;



@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public Page<ProductDTO> findAll(String search, Pageable pageable) {
        Page<Product> products;
        if (search != null && !search.trim().isEmpty()) {
            products = productRepository.findByNomeContainingIgnoreCaseOrCategoriaContainingIgnoreCase(
                search, search, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products.map(this::convertToDTO);
    }
    
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return convertToDTO(product);
    }
    
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
    
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        BeanUtils.copyProperties(productDTO, existingProduct, "id", "createdAt");
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }
    
    public void delete(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        productRepository.delete(product);
    }
    
    public ProductDTO updateStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        int newStock = product.getEstoque() + quantity;
        if (newStock < 0) {
            throw new BusinessException("Estoque insuficiente. Estoque atual: " + product.getEstoque());
        }
        
        product.setEstoque(newStock);
        Product updatedProduct = productRepository.save(product);
        
        return convertToDTO(updatedProduct);
    }
    
    public List<ProductDTO> getLowStockProducts() {
        List<Product> lowStockProducts = productRepository.findByEstoqueLessThan(10);
        return lowStockProducts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<ProductDTO> getExpiringProducts() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        List<Product> expiringProducts = productRepository.findByValidadeBefore(thirtyDaysFromNow);
        return expiringProducts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<ProductDTO> getTopSellingProducts() {
        List<Product> topProducts = productRepository.findTopSellingProducts(PageRequest.of(0, 10));
        return topProducts.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }
}