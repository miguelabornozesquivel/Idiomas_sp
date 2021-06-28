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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Sesion")
public class Sesion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message="Campo obligatorio")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="HH:mm")
	@Column(name="horaIni", nullable=false)
	private Date horaIni;
	
	@NotNull(message="Campo obligatorio")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern="HH:mm")
	@Column(name="horaFin", nullable=false)
	private Date horaFin;
	
	@Size(max=100, message="Máximo 50 caracteres")
	@Column(name="detalle", length=100, nullable=true)
	private String detalle;
	
	@ManyToOne
	@JoinColumn(name="idCurso", nullable=false)
	private Curso curso;
	
	@ManyToOne
	@JoinColumn(name="idDia", nullable=false)
	private Dia dia;
	
	public Sesion() {
		super();
	}

	public Sesion(int id, Date horaIni, Date horaFin, String detalle, Curso curso, Dia dia) {
		super();
		this.id = id;
		this.horaIni = horaIni;
		this.horaFin = horaFin;
		this.detalle = detalle;
		this.curso = curso;
		this.dia = dia;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getHoraIni() {
		return horaIni;
	}

	public void setHoraIni(Date horaIni) {
		this.horaIni = horaIni;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}
	
}
