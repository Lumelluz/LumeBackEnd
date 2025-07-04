package br.com.lume.lumemarketplace.business;

import br.com.lume.lumemarketplace.entity.Business;

public record PublicBusinessDTO(
 String nomeFantasia,
 String siteRedes,
 String certificacoesAmbientais,
 String origemDosMateriais,
 String compromissoSustentabilidade,
 String informacoesAdicionais
) {
 public static PublicBusinessDTO fromEntity(Business business) {
     if (business == null) return null;
     return new PublicBusinessDTO(
         business.getNomeFantasia(),
         business.getSiteRedes(),
         business.getCertificacoesAmbientais(),
         business.getOrigemDosMateriais(),
         business.getCompromissoSustentabilidade(),
         business.getInformacoesAdicionais()
     );
 }
}