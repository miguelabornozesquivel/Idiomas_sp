package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.repository.IProfesorRepository;
import pe.edu.upc.spring.service.IProfesorService;

@Service
public class ProfesorServiceImpl implements IProfesorService {

	@Autowired
	private IProfesorRepository dProfesor;
	
	@Override
	@Transactional
	public boolean insertar(Profesor profesor) {
		Profesor objProfesor = dProfesor.save(profesor);
		if (objProfesor == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Profesor profesor) {
		/*boolean flag = false;
		try {
			dProfesor.save(profesor);
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
		dProfesor.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Profesor> listarPorId(int id) {
		// TODO Auto-generated method stub
		return dProfesor.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Profesor> listar() {
		return dProfesor.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Profesor> buscarPorFiltro(String filtro) {
		return dProfesor.buscarPorFiltro(filtro);
	}

}
