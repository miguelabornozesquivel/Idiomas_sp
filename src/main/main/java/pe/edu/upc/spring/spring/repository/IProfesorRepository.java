package pe.edu.upc.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Profesor;

@Repository
public interface IProfesorRepository extends JpaRepository<Profesor, Integer>{
	@Query("from Profesor r where "
			+ "upper(r.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.apellido) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.correo) like '%'||upper(:filtro)||'%'")
	List<Profesor> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Profesor o where upper(trim(o.correo)) = upper(trim(:correo)) and o.id != :id")
	List<Profesor> buscarDuplicado(String correo, int id);
	
	@Query("from Profesor o where upper(o.correo) = upper(:correo)")
	Optional<Profesor> buscarPorCorreo(@Param("correo") String correo);
}
