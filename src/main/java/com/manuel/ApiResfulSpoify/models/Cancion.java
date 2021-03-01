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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="cancion")
public class Cancion {
	@ApiModelProperty(position = 0)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(position = 1)
	@NotBlank
	@Column(name="nombre")
	@NotBlank
	private String nombre;
	@ApiModelProperty(position = 2)
	@NotBlank
	@Column(name="duracion")
	private double duracion;
	@ApiModelProperty(position = 3)
	@NotBlank
	@ManyToOne(cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinColumn(name="id_genero")
	private Genero genero;
	@ApiModelProperty(position = 4)
	@NotBlank
	@JsonIgnoreProperties("listacanciones")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_disco")
	private Disco disco;

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

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Disco getDisco() {
		return disco;
	}

	public void setDisco(Disco disco) {
		this.disco = disco;
		List<Cancion> ls=this.disco.getListacanciones();
		if(ls==null) {
			ls=new ArrayList<>();
		}
		if(!ls.contains(this)) {
			ls.add(this);
		}
	}

	@Override
	public String toString() {
		return "Cancion [id=" + id + ", nombre=" + nombre + ", duracion=" + duracion + ", genero=" + genero + "]";
	}
	
	
}
