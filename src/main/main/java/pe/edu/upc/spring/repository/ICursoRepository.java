package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Curso;

@Repository
public interface ICursoRepository extends JpaRepository<Curso, Integer>{
	@Query("from Curso r where upper(r.nivel) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.enlace) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.idioma.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.profesor.nombre) like '%'||upper(:filtro)||'%'")
	List<Curso> buscarPorFiltro(@Param("filtro") String filtro);
}
