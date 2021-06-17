package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Alumno;

@Repository
public interface IAlumnoRepository extends JpaRepository<Alumno, Integer>{
	@Query("from Alumno r where "
			+ "upper(r.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.apellido) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.correo) like '%'||upper(:filtro)||'%'")
	List<Alumno> buscarPorFiltro(@Param("filtro") String filtro);
}
