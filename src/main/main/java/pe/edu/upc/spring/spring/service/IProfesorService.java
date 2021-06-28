package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Profesor;

public interface IProfesorService {
	public int insertar(Profesor profesor);
	public void eliminar(int id);
	public Optional<Profesor> listarPorId(int id);
	List<Profesor> listar();
	List<Profesor> buscarPorFiltro(String filtro);
	Optional<Profesor> buscarPorCorreo(String correo);
}
