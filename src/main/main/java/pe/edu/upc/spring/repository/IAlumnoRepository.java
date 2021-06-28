package pe.edu.upc.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Alumno;

@Repository
public interface IAlumnoRepository extends JpaRepository<Alumno, Integer>{
	@Query("from Alumno o where "
			+ "upper(o.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(o.apellido) like '%'||upper(:filtro)||'%' or "
			+ "upper(o.correo) like '%'||upper(:filtro)||'%'")
	List<Alumno> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Alumno o where upper(trim(o.correo)) = upper(trim(:correo)) and o.id != :id")
	List<Alumno> buscarDuplicado(String correo, int id);
	
	@Query("from Alumno o where upper(o.correo) = upper(:correo)")
	Optional<Alumno> buscarPorCorreo(@Param("correo") String correo);
}
