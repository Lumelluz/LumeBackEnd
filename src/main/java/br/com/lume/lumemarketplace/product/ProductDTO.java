package br.com.lume.lumemarketplace.product;

import br.com.lume.lumemarketplace.entity.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ProductDTO(
    Long id,
    String productName,
    String companyName,
    String categoria,
    String description,
    String imageUrl,
    List<String> galleryImages,
    String imageAlt,
    double originalPrice,
    double currentPrice,
    double discountPercentage,
    String installments,
    String shippingInfo,
    String benefits,
    String specialDiscount,
    String features,
    LocalDateTime date,
    String status,
    boolean isVerified,
    Long businessId,
    Long ownerId 
) {
    public static ProductDTO fromEntity(Product product, ObjectMapper objectMapper) {
        List<String> gallery;
        try {
            gallery = objectMapper.readValue(product.getGalleryImages(), new TypeReference<List<String>>() {});
        } catch (Exception e) {
            gallery = Collections.emptyList();
        }

        Long ownerId = (product.getOwner() != null) ? product.getOwner().getId() : null;
        Long businessId = (product.getOwner() != null && product.getOwner().getBusiness() != null) 
                        ? product.getOwner().getBusiness().getId() 
                        : null;

        return new ProductDTO(
            product.getId(),
            product.getProductName(),
            product.getCompanyName(),
            product.getCategoria(),
            product.getDescription(),
            product.getImageUrl(),
            gallery,
            product.getImageAlt(),
            product.getOriginalPrice(),
            product.getCurrentPrice(),
            product.getDiscountPercentage(),
            product.getInstallments(),
            product.getShippingInfo(),
            product.getBenefits(),
            product.getSpecialDiscount(),
            product.getFeatures(),
            product.getDate(),
            product.getStatus(),
            product.isVerified(),
            businessId,
            ownerId
        );
    }
}