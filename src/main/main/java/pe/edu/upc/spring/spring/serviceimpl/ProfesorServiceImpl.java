package pe.edu.upc.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Profesor;
import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.repository.IProfesorRepository;
import pe.edu.upc.spring.repository.IUsuarioRepository;
import pe.edu.upc.spring.service.IProfesorService;

@Service
public class ProfesorServiceImpl implements IProfesorService {

	@Autowired
	private IProfesorRepository dProfesor;
	
	@Autowired
	private IUsuarioRepository dUsuario;
	
	@Override
	@Transactional
	public int insertar(Profesor profesor) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		/* buscamos el id de usuario asociado al profesor */
		Long userId = Long.valueOf(0);
		if (profesor.getId() > 0) {
			Profesor obj = dProfesor.getOne(profesor.getId());
			userId = obj.getUsuario().getId();
		}
		/* buscamos profesores y usuarios con el correo ingresado */
		int rptP = dProfesor.buscarDuplicado(profesor.getCorreo(), profesor.getId()).size();
		int rptU = dUsuario.buscarDuplicado(profesor.getCorreo(), userId).size();
		/* si no se encuentra se procede a crear el registro */
		if (rptP == 0 && rptU == 0) {
			Users user = dUsuario.findByUsername(profesor.getCorreo());
			if (user == null) {
				List<Role> roles = new ArrayList<Role>();
				roles.add(new Role(Long.valueOf(0), "ROLE_PROFESOR"));
				user = new Users(Long.valueOf(0), profesor.getCorreo(), passwordEncoder.encode(profesor.getContrasena()), true, roles);
			}
			else {
				user.setUsername(profesor.getCorreo());
				user.setPassword(passwordEncoder.encode(profesor.getContrasena()));
			}
			profesor.setUsuario(dUsuario.save(user));
			dProfesor.save(profesor);
		}
		return rptP + rptU;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dProfesor.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Profesor> listarPorId(int id) {
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
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Profesor> buscarPorCorreo(String correo) {
		return dProfesor.buscarPorCorreo(correo);
	}
}
