package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Profesor;

public interface IProfesorService {
	public boolean insertar(Profesor profesor);
	public boolean modificar(Profesor profesor);
	public void eliminar(int id);
	public Optional<Profesor> listarPorId(int id);
	List<Profesor> listar();
	List<Profesor> buscarPorFiltro(String filtro);
}
