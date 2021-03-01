package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.manuel.ApiResfulSpoify.models.Cancion;


public interface CancionRepository extends JpaRepository<Cancion, Integer>{
	
	@Query(value = "SELECT * FROM cancion WHERE nombre LIKE ?1%",
			nativeQuery = true)
	List<Cancion> getCancionesByNombre(String nombre);
	@Query(value = "SELECT * FROM cancion WHERE id_disco = ?1 AND nombre = ?2",
			nativeQuery = true)
	List<Cancion> existCancion(int id_disco,String nombre);
	@Query(value = "SELECT * FROM cancion WHERE id_disco =?1",
			nativeQuery = true)
	List<Cancion> getCancionesByDisco(int id_disco);
	@Query(value = "SELECT * FROM cancion AS c JOIN lista_cancion AS lc ON c.id=lc.id_cancion JOIN lista AS l ON l.id=lc.id_lista WHERE l.id=?1",
			nativeQuery = true)
	List<Cancion> getCancionesByListaReproduccion(int id_lista);
	

}
