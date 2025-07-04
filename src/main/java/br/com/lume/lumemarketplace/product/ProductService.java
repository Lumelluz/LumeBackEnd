package br.com.lume.lumemarketplace.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lume.lumemarketplace.entity.Product;
import br.com.lume.lumemarketplace.entity.ProductRepository;
import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

    public List<ProductDTO> getApprovedProducts() {
        return productRepository.findAllByStatusWithDetails("APPROVED").stream()
                .map(product -> ProductDTO.fromEntity(product, objectMapper))
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getPendingProducts() {
        return productRepository.findAllByStatusWithDetails("PENDING").stream()
                .map(product -> ProductDTO.fromEntity(product, objectMapper))
                .collect(Collectors.toList());
    }
	
    @Transactional
    public Product updateProductByOwner(Long productId, ProductRegistrationDTO dto, String userEmail) throws Exception {
        User owner = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado."));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        if (!product.getOwner().getId().equals(owner.getId())) {
            throw new IllegalAccessException("Você não tem permissão para editar este produto.");
        }

        product.setProductName(dto.productName());
        product.setCompanyName(dto.companyName());
        product.setCategoria(dto.categoria());
        product.setDescription(dto.description());
        product.setImageUrl(dto.imageUrl());
        product.setImageAlt(dto.imageAlt());
        product.setOriginalPrice(dto.originalPrice());
        product.setCurrentPrice(dto.currentPrice());
        product.setDiscountPercentage(dto.discountPercentage());
        product.setInstallments(dto.installments());
        product.setShippingInfo(dto.shippingInfo());
        product.setSpecialDiscount(dto.specialDiscount());
        product.setBenefits(String.join(",", dto.benefits()));
        product.setFeatures(String.join(",", dto.features()));
        product.setGalleryImages(objectMapper.writeValueAsString(dto.galleryImages()));
     
        product.setStatus("PENDING");
        product.setVerified(false);

        return productRepository.save(product);
    }
	
		public Product createProduct(ProductRegistrationDTO dto, String userEmail) throws Exception {

		User owner = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new IllegalStateException("Utilizador não encontrado."));

		if (!"ROLE_BUSINESS".equals(owner.getRole())) {
			throw new IllegalStateException("Apenas contas de empresa podem cadastrar produtos.");
		}

		Product product = new Product();

		product.setOwner(owner);
		product.setProductName(dto.productName());
		product.setCompanyName(dto.companyName());
		product.setCategoria(dto.categoria());
		product.setDescription(dto.description());
		product.setImageUrl(dto.imageUrl());
		product.setImageAlt(dto.imageAlt());
		product.setOriginalPrice(dto.originalPrice());
		product.setCurrentPrice(dto.currentPrice());
		product.setDiscountPercentage(dto.discountPercentage());
		product.setInstallments(dto.installments());
		product.setShippingInfo(dto.shippingInfo());
		product.setSpecialDiscount(dto.specialDiscount());

		product.setBenefits(String.join(",", dto.benefits()));
		product.setFeatures(String.join(",", dto.features()));

		String galleryJson = objectMapper.writeValueAsString(dto.galleryImages());
		product.setGalleryImages(galleryJson);

		product.setStatus("PENDING");
		product.setVerified(false);
		product.setDate(LocalDateTime.now());

		return productRepository.save(product);
	}

    @Transactional
    public Product updateProductByAdmin(Long productId, ProductRegistrationDTO dto) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalStateException("Produto com ID " + productId + " não encontrado."));

        product.setProductName(dto.productName());
        product.setCompanyName(dto.companyName());
        product.setCategoria(dto.categoria());
        product.setDescription(dto.description());
        product.setImageAlt(dto.imageAlt());
        product.setOriginalPrice(dto.originalPrice());
        product.setCurrentPrice(dto.currentPrice());
        product.setDiscountPercentage(dto.discountPercentage());
        product.setInstallments(dto.installments());
        product.setShippingInfo(dto.shippingInfo());
        product.setSpecialDiscount(dto.specialDiscount());
        product.setBenefits(String.join(",", dto.benefits()));
        product.setFeatures(String.join(",", dto.features()));
        
        return productRepository.save(product);
    }
    
    @Transactional
    public Product approveProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalStateException("Produto com ID " + productId + " não encontrado."));
        
        product.setStatus("APPROVED");
        product.setVerified(true);
        return productRepository.save(product);
    }
}
