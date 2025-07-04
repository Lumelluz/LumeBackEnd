package br.com.lume.lumemarketplace.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    List<User> findAllByRole(String role);
    Optional<User> findByBusiness(Business business);

}