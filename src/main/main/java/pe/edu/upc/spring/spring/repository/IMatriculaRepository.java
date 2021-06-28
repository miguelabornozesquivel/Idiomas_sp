package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Matricula;

@Repository
public interface IMatriculaRepository extends JpaRepository<Matricula, Integer>{
	@Query("from Matricula r where upper(r.curso.idioma.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.curso.nivel) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.alumno.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.alumno.apellido) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.comprobante) like '%'||upper(:filtro)||'%'")
	List<Matricula> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Matricula o where o.curso = :curso and o.alumno = :alumno and o.id != :id")
	List<Matricula> buscarDuplicado(Curso curso, Alumno alumno, int id);
	
	@Query("from Matricula o where upper(o.alumno.correo) = upper(:correo)")
	List<Matricula> buscarPorCorreo(@Param("correo") String correo);
}
