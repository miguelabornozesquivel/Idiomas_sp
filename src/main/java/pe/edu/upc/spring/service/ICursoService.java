package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Curso;

public interface ICursoService {
	public boolean insertar(Curso curso);
	public boolean modificar(Curso curso);
	public void eliminar(int id);
	public Optional<Curso> listarPorId(int id);
	List<Curso> listar();
	List<Curso> buscarPorFiltro(String filtro);
}
