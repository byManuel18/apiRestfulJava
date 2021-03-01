package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiResfulSpoify.models.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Integer>{
	
	@Query(value="SELECT * FROM artista WHERE nombre LIKE ?1%",
			nativeQuery = true)
	List<Artista> getAllArtistasByNombre(String nombre);
	@Query(value="SELECT * FROM artista WHERE nacionalidad LIKE ?1%",
			nativeQuery = true)
	List<Artista> getAllArtistasByNacionalidad(String nombre);
	@Query(value="SELECT * FROM artista WHERE nombre = ?1",
			nativeQuery = true)
	List<Artista> existartista(String nombre);
}
