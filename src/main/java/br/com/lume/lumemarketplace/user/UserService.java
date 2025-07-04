package br.com.lume.lumemarketplace.user;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User updateUserProfile(String email, UserProfileUpdateDTO dto) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Utilizador não encontrado."));

		user.setNomeCompleto(dto.nomeCompleto());
		user.setDataNascimento(LocalDate.parse(dto.dataNascimento()));
		user.setTelefone(dto.telefone());
		user.setCep(dto.cep());
		user.setLogradouro(dto.logradouro());
		user.setNumero(dto.numero());
		user.setComplemento(dto.complemento());
		user.setBairro(dto.bairro());
		user.setCidade(dto.cidade());
		user.setEstado(dto.estado());

		return userRepository.save(user);
	}

	public List<UserDTO> findAllUsers() {
		return userRepository.findAll().stream().map(UserDTO::fromEntity).collect(Collectors.toList());
	}
}