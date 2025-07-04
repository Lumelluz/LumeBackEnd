package br.com.lume.lumemarketplace.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
	   @Query("SELECT p FROM Product p JOIN FETCH p.owner o LEFT JOIN FETCH o.business WHERE p.status = :status")
	    List<Product> findAllByStatusWithDetails(@Param("status") String status);
	
	List<Product> findAllByStatus(String status);
}
