package br.com.lume.lumemarketplace.user;

import br.com.lume.lumemarketplace.business.BusinessDTO;
import br.com.lume.lumemarketplace.entity.User;

public record UserDTO(
    Long id,
    String nomeCompleto,
    String email,
    String role,
    BusinessDTO business
) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
            user.getId(),
            user.getNomeCompleto(),
            user.getEmail(),
            user.getRole(),
            BusinessDTO.fromEntityWithEmail(user.getBusiness(), user.getEmail())
        );
    }
}