package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Curso;
import pe.edu.upc.spring.repository.ICursoRepository;
import pe.edu.upc.spring.service.ICursoService;

@Service
public class CursoServiceImpl implements ICursoService {

	@Autowired
	private ICursoRepository dCurso;
	
	@Override
	@Transactional
	public int insertar(Curso curso) {
		int rpta = dCurso.buscarDuplicado(curso.getEnlace(), curso.getId()).size();
		if (rpta == 0) dCurso.save(curso);
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dCurso.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Curso> listarPorId(int id) {
		return dCurso.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Curso> listar() {
		return dCurso.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Curso> buscarPorFiltro(String filtro) {
		return dCurso.buscarPorFiltro(filtro);
	}

}
