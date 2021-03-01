package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiResfulSpoify.models.Genero;

public interface GeneroRepository extends JpaRepository<Genero,Integer>{
	
	@Query(value="SELECT * FROM genero WHERE nombre LIKE ?1%",
			nativeQuery = true)
	List<Genero> getGenerosByNombre(String nombre);
	@Query(value="SELECT * FROM genero WHERE nombre=?1",
			nativeQuery = true)
	List<Genero> generoExistente(String nombre);
}
