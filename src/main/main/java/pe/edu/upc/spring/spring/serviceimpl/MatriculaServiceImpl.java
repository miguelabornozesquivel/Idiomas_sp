package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Matricula;
import pe.edu.upc.spring.repository.IMatriculaRepository;
import pe.edu.upc.spring.service.IMatriculaService;

@Service
public class MatriculaServiceImpl implements IMatriculaService {

	@Autowired
	private IMatriculaRepository dMatricula;
	
	@Override
	@Transactional
	public int insertar(Matricula matricula) {
		int rpta = dMatricula.buscarDuplicado(matricula.getCurso(), matricula.getAlumno(), matricula.getId()).size();
		if (rpta == 0) dMatricula.save(matricula);
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dMatricula.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Matricula> listarPorId(int id) {
		return dMatricula.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Matricula> listar() {
		return dMatricula.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Matricula> buscarPorFiltro(String filtro) {
		return dMatricula.buscarPorFiltro(filtro);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Matricula> buscarPorCorreo(String correo) {
		return dMatricula.buscarPorCorreo(correo);
	}
}
