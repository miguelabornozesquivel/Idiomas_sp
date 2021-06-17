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
	public boolean insertar(Idioma idioma) {
		Idioma objIdioma = dIdioma.save(idioma);
		if (objIdioma == null)
			return false;
		else
			return true;
	}
	
	@Override
	@Transactional
	public boolean modificar(Idioma idioma) {
		/*boolean flag = false;
		try {
			dIdioma.save(idioma);
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
		dIdioma.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Idioma> listarPorId(int id) {
		// TODO Auto-generated method stub
		return dIdioma.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Idioma> listar() {
		return dIdioma.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Idioma> buscarPorNombre(String nombre) {
		return dIdioma.buscarPorNombre(nombre);
	}

}
