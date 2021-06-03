package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Sesion;

public interface ISesionService {
	public boolean insertar(Sesion sesion);
	public boolean modificar(Sesion sesion);
	public void eliminar(int id);
	public Optional<Sesion> listarPorId(int id);
	List<Sesion> listar();
	List<Sesion> buscarPorFiltro(String filtro);
}
