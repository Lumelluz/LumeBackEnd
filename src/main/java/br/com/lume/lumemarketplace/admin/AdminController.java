package br.com.lume.lumemarketplace.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lume.lumemarketplace.business.BusinessDTO;
import br.com.lume.lumemarketplace.business.BusinessRegistrationDTO;
import br.com.lume.lumemarketplace.business.BusinessService;
import br.com.lume.lumemarketplace.entity.Ticket;
import br.com.lume.lumemarketplace.product.ProductDTO;
import br.com.lume.lumemarketplace.product.ProductRegistrationDTO;
import br.com.lume.lumemarketplace.product.ProductService;
import br.com.lume.lumemarketplace.ticket.TicketService;
import br.com.lume.lumemarketplace.user.UserDTO;
import br.com.lume.lumemarketplace.user.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private UserService userService;

	@GetMapping("/tickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		return ResponseEntity.ok(ticketService.getAllTickets());
	}

	@GetMapping("/products/pending")
	public ResponseEntity<List<ProductDTO>> getPendingProducts() {
		return ResponseEntity.ok(productService.getPendingProducts());
	}

	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRegistrationDTO dto) {
		try {
			productService.updateProductByAdmin(id, dto);
			return ResponseEntity.ok("Produto atualizado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/products/{id}/approve")
	public ResponseEntity<?> approveProduct(@PathVariable Long id) {
		try {
			productService.approveProduct(id);
			return ResponseEntity.ok("Produto aprovado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return ResponseEntity.ok(userService.findAllUsers());
	}

	@GetMapping("/businesses")
	public ResponseEntity<List<BusinessDTO>> getAllBusinesses() {
		return ResponseEntity.ok(businessService.getAllBusinesses());
	}

	@PutMapping("/businesses/{id}")
	public ResponseEntity<?> updateBusiness(@PathVariable Long id, @Valid @RequestBody BusinessRegistrationDTO dto) {
		try {
			businessService.updateBusinessByAdmin(id, dto);
			return ResponseEntity.ok("Dados da empresa atualizados com sucesso.");
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/businesses/{id}")
	public ResponseEntity<?> deleteBusiness(@PathVariable Long id) {
		try {
			businessService.deleteBusinessByAdmin(id);
			return ResponseEntity.ok("Empresa removida com sucesso.");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
}