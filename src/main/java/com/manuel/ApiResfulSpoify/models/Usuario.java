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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.JoinColumn;

@Entity
@Table(name="usuario")
@JsonIgnoreProperties({"subcripciones","listascreadas"}) 
public class Usuario {
	
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
	@Column(name="correo",unique = true)
	private String correo;
	@ApiModelProperty(position = 3)
	@NotBlank
	@Column(name="foto")
	private String foto;
	@ApiModelProperty(position = 5)
	@NotBlank
	@Column(name="activo")
	private boolean activo;
	@ApiModelProperty(position = 4)
	@NotBlank
	@Column(name="contra")
	private String contra;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnoreProperties("usuarios_subcritos") 
	@JoinTable(
			name="subcripciones",
				inverseJoinColumns = @JoinColumn(name="id_lista",nullable=false),
			    joinColumns = @JoinColumn(name="id_usuario",nullable=false)
			)
	@ManyToMany(cascade = {CascadeType.MERGE},fetch=FetchType.LAZY)
	List<ListaReproduccion> subcripciones=new ArrayList<ListaReproduccion>();
	@ApiModelProperty(hidden = true)
	@JsonIgnoreProperties("creador")
	@OneToMany(mappedBy="creador",cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	List<ListaReproduccion> listascreadas=new ArrayList<ListaReproduccion>();
	
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public String getFoto() {
		return foto;
	}
	public boolean isActivo() {
		return activo;
	}
	
	
	public List<ListaReproduccion> getSubcripciones() {
		return subcripciones;
	}
	public void setSubcripciones(List<ListaReproduccion> subcripciones) {
		this.subcripciones = subcripciones;
	}
	public List<ListaReproduccion> getListascreadas() {
		return listascreadas;
	}
	public void setListascreadas(List<ListaReproduccion> listascreadas) {
		this.listascreadas = listascreadas;
		this.listascreadas=listascreadas;
		for(ListaReproduccion pl:this.listascreadas) {
			pl.setCreador(this);
		}
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	public String getContra() {
		return contra;
	}
	public void setContra(String contra) {
		this.contra = contra;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", foto=" + foto + ", activo="
				+ activo + "]";
	}
	
	
	
}
