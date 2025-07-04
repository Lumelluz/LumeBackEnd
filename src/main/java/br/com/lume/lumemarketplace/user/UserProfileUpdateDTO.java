package br.com.lume.lumemarketplace.user;

import jakarta.validation.constraints.NotBlank;

public record UserProfileUpdateDTO(
 @NotBlank String nomeCompleto,
 String dataNascimento,
 @NotBlank String telefone,
 @NotBlank String cep,
 @NotBlank String logradouro,
 @NotBlank String numero,
 String complemento,
 @NotBlank String bairro,
 @NotBlank String cidade,
 @NotBlank String estado
) {}
