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
	public boolean insertar(Dia dia) {
		Dia objDia = dDia.save(dia);
		if (objDia == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Dia dia) {
		/*boolean flag = false;
		try {
			dDia.save(dia);
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
		dDia.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Dia> listarPorId(int id) {
		// TODO Auto-generated method stub
		return dDia.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Dia> listar() {
		return dDia.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Dia> buscarPorNombre(String nombre) {
		return dDia.buscarPorNombre(nombre);
	}

}
