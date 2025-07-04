package br.com.lume.lumemarketplace.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lume.lumemarketplace.config.JwtUtil;
import br.com.lume.lumemarketplace.entity.User;
import br.com.lume.lumemarketplace.entity.UserRepository;
import br.com.lume.lumemarketplace.security.CustomUserDetailsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
		try {
			authService.registerUser(registrationDTO);
			return ResponseEntity.ok("Utilizador registado com sucesso!");
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDTO loginRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Email ou senha incorretos");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
		final String jwt = jwtUtil.generateToken(userDetails);
		final Date expirationDate = jwtUtil.extractExpiration(jwt);

		final User user = userRepository.findByEmail(loginRequest.email())
				.orElseThrow(() -> new Exception("Utilizador não encontrado após autenticação"));

		return ResponseEntity.ok(AuthenticationResponse.from(jwt, user, expirationDate.getTime()));
	}
}