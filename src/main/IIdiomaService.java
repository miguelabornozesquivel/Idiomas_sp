package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Idioma;

public interface IIdiomaService {
	public boolean insertar(Idioma idioma);
	public boolean modificar(Idioma idioma);
	public void eliminar(int id);
	public Optional<Idioma> listarPorId(int id);
	List<Idioma> listar();
	List<Idioma> buscarPorNombre(String nombre);
}
