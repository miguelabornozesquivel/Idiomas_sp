package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Dia;

public interface IDiaService {
	public Integer insertar(Dia dia);
	public void eliminar(int id);
	public Optional<Dia> listarPorId(int id);
	List<Dia> listar();
	List<Dia> buscarPorFiltro(String filtro);
}
