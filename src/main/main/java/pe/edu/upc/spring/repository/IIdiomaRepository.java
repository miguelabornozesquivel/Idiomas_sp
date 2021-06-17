package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Idioma;

@Repository
public interface IIdiomaRepository extends JpaRepository<Idioma, Integer>{
	@Query("from Idioma r where upper(r.nombre) like '%'||upper(:nombre)||'%'")
	List<Idioma> buscarPorNombre(@Param("nombre") String nombre);
}
