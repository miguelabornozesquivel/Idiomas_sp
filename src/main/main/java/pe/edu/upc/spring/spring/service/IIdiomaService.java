package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Idioma;

public interface IIdiomaService {
	public Integer insertar(Idioma idioma);
	public void eliminar(int id);
	public Optional<Idioma> listarPorId(int id);
	List<Idioma> listar();
	List<Idioma> buscarPorFiltro(String filtro);
}
