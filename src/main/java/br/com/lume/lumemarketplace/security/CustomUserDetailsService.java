package br.com.lume.lumemarketplace.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado com o email: " + email));
        
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), authorities);
    }
}