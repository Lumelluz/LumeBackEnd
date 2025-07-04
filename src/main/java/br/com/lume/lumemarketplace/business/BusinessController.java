package br.com.lume.lumemarketplace.business;

import java.util.Optional;

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

import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.user.UserDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/register")
    public ResponseEntity<?> registerBusiness(@Valid @RequestBody BusinessRegistrationDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            User updatedUser = businessService.registerNewBusiness(dto, userEmail);
            UserDTO userDto = UserDTO.fromEntity(updatedUser);
            return ResponseEntity.ok(userDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateBusiness(@Valid @RequestBody BusinessRegistrationDTO dto, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            businessService.updateMyBusiness(dto, userEmail);
            return ResponseEntity.ok("Dados da empresa atualizados com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessById(@PathVariable Long id) {
        Optional<PublicBusinessDTO> businessDTO = businessService.getPublicBusinessDetails(id);
        return businessDTO.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
}