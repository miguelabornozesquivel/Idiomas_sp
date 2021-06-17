package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Alumno;

public interface IAlumnoService {
	public boolean insertar(Alumno alumno);
	public boolean modificar(Alumno alumno);
	public void eliminar(int id);
	public Optional<Alumno> listarPorId(int id);
	List<Alumno> listar();
	List<Alumno> buscarPorFiltro(String filtro);
}
