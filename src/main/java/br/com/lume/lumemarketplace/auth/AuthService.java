package br.com.lume.lumemarketplace.auth;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationDTO registrationDTO) {
        if (userRepository.findByEmail(registrationDTO.email()).isPresent()) {
            throw new IllegalStateException("O e-mail fornecido já está em uso.");
        }
        if (userRepository.findByCpf(registrationDTO.cpf()).isPresent()) {
            throw new IllegalStateException("O CPF fornecido já está cadastrado.");
        }

        User newUser = new User();
        newUser.setNomeCompleto(registrationDTO.nomeCompleto());
        newUser.setCpf(registrationDTO.cpf());
        newUser.setDataNascimento(LocalDate.parse(registrationDTO.dataNascimento()));
        newUser.setTelefone(registrationDTO.telefone());
        newUser.setEmail(registrationDTO.email());
        newUser.setSenha(passwordEncoder.encode(registrationDTO.senha()));
        newUser.setCep(registrationDTO.cep());
        newUser.setLogradouro(registrationDTO.logradouro());
        newUser.setNumero(registrationDTO.numero());
        newUser.setComplemento(registrationDTO.complemento());
        newUser.setBairro(registrationDTO.bairro());
        newUser.setCidade(registrationDTO.cidade());
        newUser.setEstado(registrationDTO.estado());
        newUser.setRole("ROLE_USER");

        return userRepository.save(newUser);
    }

}