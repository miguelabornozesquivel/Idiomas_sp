package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Matricula")
public class Matricula implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Size(max=50, message="Máximo 50 caracteres")
	@NotEmpty(message="Campo obligatorio")
	@NotBlank(message="No puede estar en blanco")
	@Column(name="comprobante", nullable=false, length=50)
	private String comprobante;
	
	@NotNull(message="Campo obligatorio")
	@PastOrPresent(message="No se permite fechas futuras")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="fechaPago", nullable=false)
	private Date fechaPago;
	
	@Positive(message="Solo se permite valores positivos")
	@Min( value= 150, message="El monto mínimo es 150")
	@Column(name="monto", nullable=false)
	private float monto;
	
	@ManyToOne
	@JoinColumn(name="idCurso", nullable=false)
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name="idAlumno", nullable=false)
	private Alumno alumno;
	
	public Matricula() {
		super();
	}

	public Matricula(int id, String comprobante, Date fechaPago, float monto, Curso curso, Alumno alumno) {
		super();
		this.id = id;
		this.comprobante = comprobante;
		this.fechaPago = fechaPago;
		this.monto = monto;
		this.curso = curso;
		this.alumno = alumno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComprobante() {
		return comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	
}
