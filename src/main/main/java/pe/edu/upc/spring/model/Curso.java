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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Curso")
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nivel", length=50, nullable=false)
	private String nivel;
	
	@Column(name="enlace", length=100, nullable=false)
	private String enlace;
	
	@Column(name="duracion", nullable=false)
	private int duracion;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="fechaIni", nullable=false)
	private Date fechaIni;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="fechaFin", nullable=false)
	private Date fechaFin;
	
	@ManyToOne
	@JoinColumn(name="idIdioma", nullable=false)
	private Idioma idioma;
	
	@ManyToOne
	@JoinColumn(name="idProfesor", nullable=false)
	private Profesor profesor;
	
	public Curso() {
		super();
	}

	public Curso(int id, String nivel, String enlace, int duracion, Date fechaIni, Date fechaFin, Idioma idioma, Profesor profesor) {
		super();
		this.nivel = nivel;
		this.enlace = enlace;
		this.duracion = duracion;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.idioma = idioma;
		this.profesor = profesor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
}
