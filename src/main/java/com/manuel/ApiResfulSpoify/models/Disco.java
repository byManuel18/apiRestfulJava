package com.manuel.ApiResfulSpoify.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties("listacanciones")
@Table(name="disco")
public class Disco {
	
	@ApiModelProperty(position = 0)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@ApiModelProperty(position = 1)
	@NotBlank
	@Column(name="nombre")
	private String nombre;
	@ApiModelProperty(position = 2)
	@NotBlank
	@Column(name="foto")
	private String foto;
	@ApiModelProperty(position = 3)
	@NotBlank
	@Column(name="fecha_produccion")
	private Date fecha_produccion;
	@ApiModelProperty(position = 4)
	@NotBlank
	@JsonIgnoreProperties("listaDiscos")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_artista")
	private Artista artista;
	
	@ApiModelProperty(hidden = true)
	@OneToMany(mappedBy = "disco",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	private List<Cancion> listacanciones= new ArrayList<>();

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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getFecha_produccion() {
		return fecha_produccion;
	}

	public void setFecha_produccion(Date fecha_produccion) {
		this.fecha_produccion = fecha_produccion;
	}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
		List<Disco> ld=this.artista.getListaDiscos();
		if(ld==null) {
			ld=new ArrayList<Disco>();
		}
		if(!ld.contains(this)) {
			ld.add(this);
		}
	}

	public List<Cancion> getListacanciones() {
		return listacanciones;
	}

	public void setListacanciones(List<Cancion> listacanciones) {
		this.listacanciones = listacanciones;
		for(Cancion s:this.listacanciones) {
			s.setDisco(this);
		}
	}

	@Override
	public String toString() {
		return "Disco [id=" + id + ", nombre=" + nombre + ", foto=" + foto + ", fecha_produccion=" + fecha_produccion
				+ ", artista=" + artista + "]";
	}
	
	
}
