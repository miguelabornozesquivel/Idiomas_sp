package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="Profesor")
public class Profesor implements Serializable {

	public interface StepOneVal {
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El nombre del Profesor no puede contener caracteres especiales", groups = {StepOneVal.class})
	@Pattern(regexp = "[^0-9]+", message = "El nombre del Profesor no puede contener números", groups = {StepOneVal.class})
	@Size(max=50, message="Máximo 50 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	@Column(name="nombre", length=50, nullable=false)
	private String nombre;

	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "El apellido del Profesor no puede contener caracteres especiales", groups = {StepOneVal.class})
	@Pattern(regexp = "[^0-9]+", message = "El apellido del Profesor no puede contener números", groups = {StepOneVal.class})
	@Size(max=50, message="Máximo 50 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	@Column(name="apellido", length=50, nullable=false)
	private String apellido;
	
	@Email(message="Formato inválido, debe ser en formato de correo", groups = {StepOneVal.class})
	@Size(max=50, message="Máximo 50 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	@Column(name="correo", length=50, nullable=false)
	private String correo;
	
	@Transient
	@Size(min=7, max=20, message="Debe tener entre 7 y 20 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	private String contrasena;
	
	@OneToOne
	@JoinColumn(name="usuario_id", nullable=false)
	private Users usuario;
	
	public Profesor() {
		super();
	}

	public Profesor(int id, String nombre, String apellido, String correo, String contrasena, Users usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.contrasena = contrasena;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}
}
