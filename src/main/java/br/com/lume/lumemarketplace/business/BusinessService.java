package br.com.lume.lumemarketplace.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lume.lumemarketplace.entity.Business;
import br.com.lume.lumemarketplace.entity.BusinessRepository;
import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;

@Service
public class BusinessService {

	@Autowired
	private BusinessRepository businessRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User registerNewBusiness(BusinessRegistrationDTO dto, String userEmail) {
		if (businessRepository.findByCnpj(dto.cnpj()).isPresent()) {
			throw new IllegalStateException("O CNPJ fornecido já está cadastrado.");
		}

		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new IllegalStateException("Utilizador não encontrado."));

		Business newBusiness = new Business();
		newBusiness.setRazaoSocial(dto.razaoSocial());
		newBusiness.setNomeFantasia(dto.nomeFantasia());
		newBusiness.setCnpj(dto.cnpj());
		newBusiness.setEnderecoComercial(dto.enderecoComercial());
		newBusiness.setSiteRedes(dto.siteRedes());
		newBusiness.setCertificacoesAmbientais(dto.certificacoesAmbientais());
		newBusiness.setOrigemDosMateriais(dto.origemDosMateriais());
		newBusiness.setCompromissoSustentabilidade(dto.compromissoSustentabilidade());
		newBusiness.setInformacoesAdicionais(dto.informacoesAdicionais());

		user.setRole("ROLE_BUSINESS");
		user.setBusiness(newBusiness);

		return userRepository.save(user);
	}

	public List<BusinessDTO> getAllBusinesses() {
		return userRepository.findAllByRole("ROLE_BUSINESS").stream().filter(user -> user.getBusiness() != null)
				.map(user -> BusinessDTO.fromEntityWithEmail(user.getBusiness(), user.getEmail()))
				.collect(Collectors.toList());
	}

	@Transactional
	public Business updateBusinessByAdmin(Long businessId, BusinessRegistrationDTO dto) {
		Business business = businessRepository.findById(businessId)
				.orElseThrow(() -> new IllegalStateException("Empresa com ID " + businessId + " não encontrada."));

		businessRepository.findByCnpj(dto.cnpj()).ifPresent(existingBusiness -> {
			if (!existingBusiness.getId().equals(businessId)) {
				throw new IllegalStateException("O CNPJ " + dto.cnpj() + " já está em uso por outra empresa.");
			}
		});

		business.setRazaoSocial(dto.razaoSocial());
		business.setNomeFantasia(dto.nomeFantasia());
		business.setCnpj(dto.cnpj());
		business.setEnderecoComercial(dto.enderecoComercial());
		business.setSiteRedes(dto.siteRedes());
		business.setCertificacoesAmbientais(dto.certificacoesAmbientais());
		business.setOrigemDosMateriais(dto.origemDosMateriais());
		business.setCompromissoSustentabilidade(dto.compromissoSustentabilidade());
		business.setInformacoesAdicionais(dto.informacoesAdicionais());

		return businessRepository.save(business);
	}
	
    public Optional<PublicBusinessDTO> getPublicBusinessDetails(Long id) {
        return businessRepository.findById(id)
                .map(PublicBusinessDTO::fromEntity);
    }

	@Transactional
	public void deleteBusinessByAdmin(Long businessId) {
		Business business = businessRepository.findById(businessId)
				.orElseThrow(() -> new IllegalStateException("Empresa com ID " + businessId + " não encontrada."));

		User user = userRepository.findByBusiness(business).orElse(null);

		if (user != null) {
			user.setBusiness(null);
			user.setRole("ROLE_USER");
			userRepository.save(user);
		}

		businessRepository.deleteById(businessId);
	}

	@Transactional
	public Business updateMyBusiness(BusinessRegistrationDTO dto, String userEmail) {
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new IllegalStateException("Utilizador não encontrado."));

		Business business = user.getBusiness();
		if (business == null) {
			throw new IllegalStateException("Este utilizador não possui uma empresa associada.");
		}

		businessRepository.findByCnpj(dto.cnpj()).ifPresent(existingBusiness -> {
			if (!existingBusiness.getId().equals(business.getId())) {
				throw new IllegalStateException("O CNPJ " + dto.cnpj() + " já está em uso por outra empresa.");
			}
		});

		business.setRazaoSocial(dto.razaoSocial());
		business.setNomeFantasia(dto.nomeFantasia());
		business.setCnpj(dto.cnpj());
		business.setEnderecoComercial(dto.enderecoComercial());
		business.setSiteRedes(dto.siteRedes());
		business.setCertificacoesAmbientais(dto.certificacoesAmbientais());
		business.setOrigemDosMateriais(dto.origemDosMateriais());
		business.setCompromissoSustentabilidade(dto.compromissoSustentabilidade());
		business.setInformacoesAdicionais(dto.informacoesAdicionais());

		return businessRepository.save(business);
	}
}