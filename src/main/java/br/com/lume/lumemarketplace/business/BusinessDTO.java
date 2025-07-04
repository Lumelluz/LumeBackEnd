package br.com.lume.lumemarketplace.business;

import br.com.lume.lumemarketplace.entity.Business;

public record BusinessDTO(
 Long id,
 String razaoSocial, String nomeFantasia, String cnpj, String enderecoComercial,
 String siteRedes, String certificacoesAmbientais, String origemDosMateriais,
 String compromissoSustentabilidade, String informacoesAdicionais,
 String responsavelEmail
) {
 public static BusinessDTO fromEntityWithEmail(Business business, String email) {
     if (business == null) return null;
     return new BusinessDTO(
         business.getId(),
         business.getRazaoSocial(), business.getNomeFantasia(), business.getCnpj(),
         business.getEnderecoComercial(), business.getSiteRedes(),
         business.getCertificacoesAmbientais(), business.getOrigemDosMateriais(),
         business.getCompromissoSustentabilidade(), business.getInformacoesAdicionais(),
         email
     );
 }
}
