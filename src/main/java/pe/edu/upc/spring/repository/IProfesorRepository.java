package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Profesor;

@Repository
public interface IProfesorRepository extends JpaRepository<Profesor, Integer>{
	@Query("from Profesor r where "
			+ "upper(r.nombre) like '%'||upper(:nombre)||'%' or "
			+ "upper(r.apellido) like '%'||upper(:nombre)||'%' or "
			+ "upper(r.correo) like '%'||upper(:nombre)||'%'")
	List<Profesor> buscarPorNombre(@Param("nombre") String nombre);
}
