package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.repository.IAlumnoRepository;
import pe.edu.upc.spring.service.IAlumnoService;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

	@Autowired
	private IAlumnoRepository dAlumno;
	
	@Override
	@Transactional
	public boolean insertar(Alumno alumno) {
		Alumno objAlumno = dAlumno.save(alumno);
		if (objAlumno == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Alumno alumno) {
		/*boolean flag = false;
		try {
			dAlumno.save(alumno);
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
		dAlumno.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> listarPorId(int id) {
		// TODO Auto-generated method stub
		return dAlumno.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Alumno> listar() {
		return dAlumno.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Alumno> buscarPorFiltro(String filtro) {
		return dAlumno.buscarPorFiltro(filtro);
	}

}
