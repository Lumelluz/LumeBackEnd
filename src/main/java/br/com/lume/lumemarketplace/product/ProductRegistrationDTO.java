package br.com.lume.lumemarketplace.product;

import java.util.List;

public record ProductRegistrationDTO(
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
 List<String> benefits,
 String specialDiscount,
 List<String> features
) {}