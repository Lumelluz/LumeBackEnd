package br.com.lume.lumemarketplace.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactFormDTO(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String subject,
    @NotBlank String message
) {}