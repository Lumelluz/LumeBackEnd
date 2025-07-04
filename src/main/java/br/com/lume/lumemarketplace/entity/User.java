package br.com.lume.lumemarketplace.entity;

import java.time.LocalDate;

import br.com.lume.lumemarketplace.config.crypto.AttributeEncryptor;
import br.com.lume.lumemarketplace.config.crypto.LocalDateEncryptor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Convert(converter = AttributeEncryptor.class)
	private String nomeCompleto;
    
    @Convert(converter = AttributeEncryptor.class)
	@Column(unique = true, nullable = false, length = 256)
	private String cpf;
    
    @Convert(converter = LocalDateEncryptor.class)
    private LocalDate dataNascimento;
    
    @Convert(converter = AttributeEncryptor.class)
	private String telefone;

    @Convert(converter = AttributeEncryptor.class)
	@Column(unique = true, nullable = false)
	private String email;
    
	@Column(nullable = false)
	private String senha;

	@Convert(converter = AttributeEncryptor.class)
	private String cep;
	
	@Convert(converter = AttributeEncryptor.class)
	private String logradouro;
	
	@Convert(converter = AttributeEncryptor.class)
	private String numero;
	
	@Convert(converter = AttributeEncryptor.class)
	private String complemento;
	
	@Convert(converter = AttributeEncryptor.class)
	private String bairro;
	
	@Convert(converter = AttributeEncryptor.class)
	private String cidade;
	
	@Convert(converter = AttributeEncryptor.class)
	private String estado;

	@Column(nullable = false)
	private String role; // "ROLE_USER", "ROLE_ADMIN", "ROLE_BUSINESS"

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "business_id", referencedColumnName = "id")
	private Business business;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}
	
}