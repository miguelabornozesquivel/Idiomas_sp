package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Idioma;
import pe.edu.upc.spring.repository.IIdiomaRepository;
import pe.edu.upc.spring.service.IIdiomaService;

@Service
public class IdiomaServiceImpl implements IIdiomaService {

	@Autowired
	private IIdiomaRepository dIdioma;
	
	@Override
	@Transactional
	public Integer insertar(Idioma idioma) {
		int rpta = dIdioma.buscarDuplicado(idioma.getNombre(), idioma.getId()).size();
		if (rpta == 0) dIdioma.save(idioma);
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dIdioma.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Idioma> listarPorId(int id) {
		return dIdioma.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Idioma> listar() {
		return dIdioma.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Idioma> buscarPorFiltro(String filtro) {
		return dIdioma.buscarPorFiltro(filtro);
	}

}
