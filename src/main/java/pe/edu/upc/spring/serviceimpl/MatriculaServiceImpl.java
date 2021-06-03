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
	public boolean insertar(Matricula matricula) {
		Matricula objMatricula = dMatricula.save(matricula);
		if (objMatricula == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Matricula matricula) {
		/*boolean flag = false;
		try {
			dMatricula.save(matricula);
			flag = true;
		}
		catch(Exception ex) {
			System.out.println("Sucedió un roche...");
		}*/
		return true;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dMatricula.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Matricula> listarPorId(int id) {
		// TODO Auto-generated method stub
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

}
