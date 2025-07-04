package br.com.lume.lumemarketplace.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
 @NotBlank String nomeCompleto,
 
 @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos.")
 @NotBlank String cpf,
 
 String dataNascimento,
 
 @NotBlank @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter entre 10 e 11 dígitos.")
 @NotBlank String telefone,
 
 @NotBlank @Email String email,
 @NotBlank @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") String senha,
 
 @NotBlank String cep,
 @NotBlank String logradouro,
 @NotBlank String numero,
 String complemento,
 @NotBlank String bairro,
 @NotBlank String cidade,
 @NotBlank String estado
) {}