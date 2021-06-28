package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Idioma;

@Repository
public interface IIdiomaRepository extends JpaRepository<Idioma, Integer>{
	@Query("from Idioma o where upper(o.nombre) like '%'||upper(:filtro)||'%'")
	List<Idioma> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Idioma o where upper(trim(o.nombre)) = upper(trim(:nombre)) and o.id != :id")
	List<Idioma> buscarDuplicado(String nombre, int id);
}
