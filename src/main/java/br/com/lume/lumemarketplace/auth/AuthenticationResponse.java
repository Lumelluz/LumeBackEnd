package br.com.lume.lumemarketplace.auth;

import br.com.lume.lumemarketplace.entity.User;

public record AuthenticationResponse(
 String jwt,
 User user,
 long expiresIn
) {
 public static AuthenticationResponse from(String jwt, User user, long expiresIn) {
     user.setSenha(null); 
     return new AuthenticationResponse(jwt, user, expiresIn);
 }
}