package br.com.lume.lumemarketplace.entity;

import br.com.lume.lumemarketplace.config.crypto.AttributeEncryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AttributeEncryptor.class)
    private String razaoSocial;
    
    @Convert(converter = AttributeEncryptor.class)
    private String nomeFantasia;

    @Convert(converter = AttributeEncryptor.class)
    @Column(unique = true, nullable = false)
    private String cnpj;
    
    @Convert(converter = AttributeEncryptor.class)
    private String enderecoComercial;
    
    @Convert(converter = AttributeEncryptor.class)
    private String siteRedes;
    
    @Column(length = 1000)
    private String certificacoesAmbientais;
    
    @Column(length = 1000)
    private String origemDosMateriais;

    @Column(length = 1000)
    private String compromissoSustentabilidade;

    @Column(length = 1000)
    private String informacoesAdicionais;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEnderecoComercial() {
		return enderecoComercial;
	}

	public void setEnderecoComercial(String enderecoComercial) {
		this.enderecoComercial = enderecoComercial;
	}

	public String getSiteRedes() {
		return siteRedes;
	}

	public void setSiteRedes(String siteRedes) {
		this.siteRedes = siteRedes;
	}

	public String getCertificacoesAmbientais() {
		return certificacoesAmbientais;
	}

	public void setCertificacoesAmbientais(String certificacoesAmbientais) {
		this.certificacoesAmbientais = certificacoesAmbientais;
	}

	public String getOrigemDosMateriais() {
		return origemDosMateriais;
	}

	public void setOrigemDosMateriais(String origemDosMateriais) {
		this.origemDosMateriais = origemDosMateriais;
	}

	public String getCompromissoSustentabilidade() {
		return compromissoSustentabilidade;
	}

	public void setCompromissoSustentabilidade(String compromissoSustentabilidade) {
		this.compromissoSustentabilidade = compromissoSustentabilidade;
	}

	public String getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(String informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

    
}