package com.manuel.ApiResfulSpoify.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties("listaDiscos")
@Table(name="artista")
public class Artista {
	@ApiModelProperty(position = 0)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(position = 1)
	@NotBlank
	@Column(name="nombre",unique = true)
	private String nombre;
	@ApiModelProperty(position = 2)
	@NotBlank
	@Column(name="foto")
	private String foto;
	@ApiModelProperty(position = 3)
	@NotBlank
	@Column(name="nacionalidad")
	private String nacionalidad;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnoreProperties("artista")
	@OneToMany(mappedBy = "artista",cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	private List<Disco> listaDiscos=new ArrayList<Disco>();

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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public List<Disco> getListaDiscos() {
		return listaDiscos;
	}

	public void setListaDiscos(List<Disco> listaDiscos) {
		this.listaDiscos = listaDiscos;
		for(Disco d:this.listaDiscos) {
			d.setArtista(this);
		}
	}

	@Override
	public String toString() {
		return "Artista [id=" + id + ", nombre=" + nombre + ", foto=" + foto + ", nacionalidad=" + nacionalidad + "]";
	}
	
	
	

}
