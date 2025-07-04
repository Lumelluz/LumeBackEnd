package br.com.lume.lumemarketplace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                //config.setAllowedOrigins(List.of("http://10.0.1.4:5173/"));
                config.setAllowedOrigins(List.of("http://localhost:5173/"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            // --- CONFIGURAÇÃO DE AUTORIZAÇÃO CORRIGIDA E MAIS EXPLÍCITA ---
            .authorizeHttpRequests(auth -> auth
                // 1. Regras Públicas (não exigem login)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/{productId}", "/api/products/{productId}/questions").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/business/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/questions/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/contact").permitAll()

                // 2. Regras para Utilizadores Autenticados (qualquer papel)
                .requestMatchers(HttpMethod.POST, "/api/products/{productId}/questions").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/users/profile").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/business/register").authenticated()

                // 3. Regras Específicas para Empresas
                .requestMatchers(HttpMethod.PUT, "/api/business/update").hasRole("BUSINESS")
                .requestMatchers(HttpMethod.POST, "/api/products/register").hasRole("BUSINESS")
                .requestMatchers(HttpMethod.POST, "/api/questions/{questionId}/answer").hasRole("BUSINESS")
                .requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("BUSINESS")

                
                // 4. Regras para Administradores
                .requestMatchers("/api/admin/**").hasRole("ADMIN") 
                
                
                // 5. Qualquer outra requisição é negada por segurança
                .anyRequest().denyAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
