package com.manuel.ApiResfulSpoify.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="genero")
public class Genero {
	
	@ApiModelProperty(position = 0)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(position = 1)
	@NotBlank
	@Column(name="nombre",unique = true)
	private String nombre;
	
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Genero [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
