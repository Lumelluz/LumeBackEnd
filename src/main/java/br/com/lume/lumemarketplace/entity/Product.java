package br.com.lume.lumemarketplace.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User owner;

	private String productName;
	private String companyName;
	private String categoria;

	@Column(length = 2048)
	private String description;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String imageUrl;

	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String galleryImages;

	private String imageAlt;
	private double originalPrice;
	private double currentPrice;
	private double discountPercentage;
	private String installments;
	private String shippingInfo;
	
	@Column(length = 1000)
	private String benefits;
	private String specialDiscount;
	
	@Column(length = 1000)
	private String features;
	
	private LocalDateTime date;
	private String status; // "PENDING", "APPROVED", "REJECTED"
	private boolean isVerified;

	public Long getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getGalleryImages() {
		return galleryImages;
	}

	public void setGalleryImages(String galleryImages) {
		this.galleryImages = galleryImages;
	}

	public String getImageAlt() {
		return imageAlt;
	}

	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getInstallments() {
		return installments;
	}

	public void setInstallments(String installments) {
		this.installments = installments;
	}

	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public String getBenefits() {
		return benefits;
	}

	public void setBenefits(String benefits) {
		this.benefits = benefits;
	}

	public String getSpecialDiscount() {
		return specialDiscount;
	}

	public void setSpecialDiscount(String specialDiscount) {
		this.specialDiscount = specialDiscount;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

}