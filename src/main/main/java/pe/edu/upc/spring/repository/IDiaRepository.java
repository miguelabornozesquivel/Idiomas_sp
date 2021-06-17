package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Dia;

@Repository
public interface IDiaRepository extends JpaRepository<Dia, Integer>{
	@Query("from Dia r where upper(r.nombre) like '%'||upper(:nombre)||'%'")
	List<Dia> buscarPorNombre(@Param("nombre") String nombre);
}
