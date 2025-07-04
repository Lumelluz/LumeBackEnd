package br.com.lume.lumemarketplace.business;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record BusinessRegistrationDTO(
    @NotBlank String razaoSocial,
    @NotBlank String nomeFantasia,
    @NotBlank @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos.") String cnpj,
    @NotBlank String enderecoComercial,
    String siteRedes,
    String certificacoesAmbientais,
    String origemDosMateriais,
    String compromissoSustentabilidade,
    String informacoesAdicionais
) {}
