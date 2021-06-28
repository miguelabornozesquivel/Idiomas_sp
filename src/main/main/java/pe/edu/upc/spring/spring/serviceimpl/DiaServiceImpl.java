package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Dia;
import pe.edu.upc.spring.repository.IDiaRepository;
import pe.edu.upc.spring.service.IDiaService;

@Service
public class DiaServiceImpl implements IDiaService {

	@Autowired
	private IDiaRepository dDia;
	
	@Override
	@Transactional
	public Integer insertar(Dia dia) {
		int rpta = dDia.buscarDuplicado(dia.getNombre(), dia.getId()).size();
		if (rpta == 0) dDia.save(dia);
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dDia.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Dia> listarPorId(int id) {
		return dDia.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Dia> listar() {
		return dDia.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Dia> buscarPorFiltro(String filtro) {
		return dDia.buscarPorFiltro(filtro);
	}

}
