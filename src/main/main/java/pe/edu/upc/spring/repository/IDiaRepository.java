package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Dia;

@Repository
public interface IDiaRepository extends JpaRepository<Dia, Integer>{
	@Query("from Dia o where upper(o.nombre) like '%'||upper(:filtro)||'%'")
	List<Dia> buscarPorFiltro(@Param("filtro") String filtro);
	
	@Query("from Dia o where upper(trim(o.nombre)) = upper(trim(:nombre)) and o.id != :id")
	List<Dia> buscarDuplicado(String nombre, int id);
}
