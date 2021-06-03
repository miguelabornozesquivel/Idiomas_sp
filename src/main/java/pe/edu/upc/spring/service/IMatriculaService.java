package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Matricula;

public interface IMatriculaService {
	public boolean insertar(Matricula matricula);
	public boolean modificar(Matricula matricula);
	public void eliminar(int id);
	public Optional<Matricula> listarPorId(int id);
	List<Matricula> listar();
	List<Matricula> buscarPorFiltro(String filtro);
}
