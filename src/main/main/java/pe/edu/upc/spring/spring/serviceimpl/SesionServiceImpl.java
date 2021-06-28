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
	public int insertar(Sesion sesion) {
		int rpta = dSesion.buscarDuplicado(sesion.getCurso(), sesion.getDia(), sesion.getHoraIni(), sesion.getHoraFin(), sesion.getId()).size();
		if (rpta == 0) dSesion.save(sesion);
		return rpta;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dSesion.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Sesion> listarPorId(int id) {
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
