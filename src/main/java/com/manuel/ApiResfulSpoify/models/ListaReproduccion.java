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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties({"usuarios_subcritos","canciones"})
@Table(name="lista")
public class ListaReproduccion {
	
	@ApiModelProperty(position = 0)
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(position = 1)
	@NotBlank
	@Column(name="nombre")
	private String nombre;
	@ApiModelProperty(position = 2)
	@NotBlank
	@Column(name="descripcion")
	private String descripcion;
	@ApiModelProperty(position = 3)
	@NotBlank
	@JsonIgnoreProperties({"subcripciones","listascreadas"})
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_usuario")
	private Usuario creador;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnoreProperties({"subcripciones"}) 
	@JoinTable(
			name="subcripciones",
			joinColumns = @JoinColumn(name="id_lista",nullable=false),
			inverseJoinColumns = @JoinColumn(name="id_usuario",nullable=false)
			)
	@ManyToMany(cascade = {CascadeType.MERGE},fetch=FetchType.LAZY)
	private List<Usuario> usuarios_subcritos=new ArrayList<Usuario>();
	
	@ApiModelProperty(hidden = true)
	@JoinTable(
			name="lista_cancion",
			joinColumns = @JoinColumn(name="id_lista",nullable=false),
			inverseJoinColumns = @JoinColumn(name="id_cancion",nullable=false)
			)
	@ManyToMany(cascade = {CascadeType.MERGE},fetch=FetchType.LAZY)
	private List<Cancion> canciones=new ArrayList<Cancion>();

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
		List<ListaReproduccion> lp=this.creador.getListascreadas();
		if(lp==null) {
			lp=new ArrayList<ListaReproduccion>();
		}
		
		if(!lp.contains(this)) {
			lp.add(this);
		}
	}

	public List<Usuario> getUsuarios_subcritos() {
		return usuarios_subcritos;
	}

	public void setUsuarios_subcritos(List<Usuario> usuarios_subcritos) {
		this.usuarios_subcritos = usuarios_subcritos;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	@Override
	public String toString() {
		return "ListaReproduccion [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}
	
	
}
