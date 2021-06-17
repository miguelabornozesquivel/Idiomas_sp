package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Sesion;
import pe.edu.upc.spring.repository.ISesionRepository;
import pe.edu.upc.spring.service.ISesionService;

@Service
public class SesionServiceImpl implements ISesionService {

	@Autowired
	private ISesionRepository dSesion;
	
	@Override
	@Transactional
	public boolean insertar(Sesion sesion) {
		Sesion objSesion = dSesion.save(sesion);
		if (objSesion == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Sesion sesion) {
		/*boolean flag = false;
		try {
			dSesion.save(sesion);
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
		dSesion.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Sesion> listarPorId(int id) {
		// TODO Auto-generated method stub
		return dSesion.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Sesion> listar() {
		return dSesion.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Sesion> buscarPorFiltro(String filtro) {
		return dSesion.buscarPorFiltro(filtro);
	}

}
