package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Users;

@Repository
public interface IUsuarioRepository extends JpaRepository<Users, Long> {
	public Users findByUsername(String username);
	
	@Query("from Users o where upper(trim(o.username)) = upper(trim(:correo)) and o.id != :id")
	List<Users> buscarDuplicado(String correo, Long id);
}