package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiResfulSpoify.models.Disco;

public interface DiscoRepository extends JpaRepository<Disco, Integer>{
	@Query(value = "SELECT * FROM disco WHERE id_artista = ?1 AND nombre =?2",
			nativeQuery = true)
	List<Disco> existDisco(int id_artista,String nombre);
	@Query(value="SELECT * FROM disco WHERE nombre LIKE ?1%",
			nativeQuery = true)
	List<Disco> getDiscosByNombre(String nombre);
	@Query(value = "SELECT * FROM disco WHERE id_artista= ?1",
			nativeQuery = true)
	List<Disco> getDiscosByArtista(int id_artista);
}
