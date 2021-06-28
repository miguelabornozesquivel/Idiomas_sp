package pe.edu.upc.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.model.Sesion;

@Repository
public interface ISesionRepository extends JpaRepository<Sesion, Integer>{
	@Query("from Sesion r where upper(r.curso.idioma.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.curso.nivel) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.dia.nombre) like '%'||upper(:filtro)||'%' or "
			+ "upper(r.detalle) like '%'||upper(:filtro)||'%'")
	List<Sesion> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Sesion o where ((:horaIni between o.horaIni and o.horaFin) or "
			+ "(:horaFin between o.horaIni and o.horaFin)) and "
			+ "o.curso = :curso and o.dia = :dia and o.id != :id")
	List<Sesion> buscarDuplicado(Curso curso, Dia dia, Date horaIni, Date horaFin, int id);
}
