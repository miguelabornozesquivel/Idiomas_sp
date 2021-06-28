package pe.edu.upc.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Alumno;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.repository.IAlumnoRepository;
import pe.edu.upc.spring.repository.IUsuarioRepository;
import pe.edu.upc.spring.service.IAlumnoService;

@Service
public class AlumnoServiceImpl implements IAlumnoService {

	@Autowired
	private IAlumnoRepository dAlumno;
	
	@Autowired
	private IUsuarioRepository dUsuario;
	
	@Override
	@Transactional
	public int insertar(Alumno alumno) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		/* buscamos el id de usuario asociado al alumno */
		Long userId = Long.valueOf(0);
		if (alumno.getId() > 0) {
			Alumno obj = dAlumno.getOne(alumno.getId());
			userId = obj.getUsuario().getId();
		}
		/* buscamos alumnos y usuarios con el correo ingresado */
		int rptA = dAlumno.buscarDuplicado(alumno.getCorreo(), alumno.getId()).size();
		int rptU = dUsuario.buscarDuplicado(alumno.getCorreo(), userId).size();
		/* si no se encuentra se procede a crear el registro */
		if (rptA == 0 && rptU == 0) {
			Users user = dUsuario.findByUsername(alumno.getCorreo());
			if (user == null) {
				List<Role> roles = new ArrayList<Role>();
				roles.add(new Role(Long.valueOf(0), "ROLE_ALUMNO"));
				user = new Users(Long.valueOf(0), alumno.getCorreo(), passwordEncoder.encode(alumno.getContrasena()), true, roles);
			}
			else {
				user.setUsername(alumno.getCorreo());
				user.setPassword(passwordEncoder.encode(alumno.getContrasena()));
			}
			alumno.setUsuario(dUsuario.save(user));
			dAlumno.save(alumno);		
		}
		return rptA + rptU;
	}

	@Override
	@Transactional
	public void eliminar(int id) {
		dAlumno.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> listarPorId(int id) {
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
	
	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> buscarPorCorreo(String correo) {
		return dAlumno.buscarPorCorreo(correo);
	}
}
