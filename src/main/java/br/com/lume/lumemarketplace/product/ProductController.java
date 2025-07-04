package br.com.lume.lumemarketplace.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lume.lumemarketplace.entity.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<ProductDTO> getAllApprovedProducts() {
        return productService.getApprovedProducts();
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> createProduct(@RequestBody ProductRegistrationDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Product newProduct = productService.createProduct(dto, userEmail);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar produto: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRegistrationDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            productService.updateProductByOwner(id, dto, userEmail);
            return ResponseEntity.ok("Produto atualizado e enviado para nova verificação.");
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}