package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import javax.persistence.Transient;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Curso")
public class Curso implements Serializable {

	public interface StepOneVal {
        /* Interfaz para la validación por grupos. 
         * @Valid en controlador genera problemas con @Transient
         */
    }
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Pattern(regexp = "[^!\"#$%&'()*+,-./:;<=>?@^_`{|}~]+", message = "No puede contener caracteres especiales",groups = {StepOneVal.class})
	@Size(max=50, message="Máximo 50 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	@Column(name="nivel", length=50, nullable=false)
	private String nivel;
	
	@Size(max=100, message="Máximo 100 caracteres", groups = {StepOneVal.class})
	@NotEmpty(message="Campo obligatorio", groups = {StepOneVal.class})
	@NotBlank(message="No puede estar en blanco", groups = {StepOneVal.class})
	@Column(name="enlace", length=100, nullable=false)
	private String enlace;
	
	@Transient
	@Column(name="duracion", nullable=false)
	private int duracion;
	
	@NotNull(message="Campo obligatorio", groups = {StepOneVal.class})
	@FutureOrPresent(message="No se permite fechas pasadas", groups = {StepOneVal.class})
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="fechaIni", nullable=false)
	private Date fechaIni;
	
	@NotNull(message="Campo obligatorio", groups = {StepOneVal.class})
	@FutureOrPresent(message="No se permite fechas pasadas", groups = {StepOneVal.class})
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
		if (this.fechaIni == null || this.fechaFin == null) return 0;
		LocalDate dtIni = LocalDate.parse(fechaIni.toString());	
		LocalDate dtFin = LocalDate.parse(fechaFin.toString());	
	    return (int)ChronoUnit.DAYS.between(dtIni, dtFin) / 7;
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
