package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiResfulSpoify.models.ListaReproduccion;

public interface ListaReproducciónRepository extends JpaRepository<ListaReproduccion, Integer>{
	
	@Query(value="SELECT * FROM lista WHERE id_usuario= ?1",
			nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionCreadasByUsuario(int id_usuario);//
	@Query(value="SELECT * FROM lista WHERE id_usuario= ?1 and nombre LIKE ?2%",
			nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionCreadasByUsuarioAndNombre(int id_usuario, String nombre);//
	@Query(value="SELECT * FROM lista WHERE id_usuario=?1 AND nombre=?2",
			nativeQuery = true)
	List<ListaReproduccion> existListaRproduccion(int id_usuario, String nombre);//
	@Query(value="SELECT * FROM lista as list JOIN subcripciones as sub ON sub.id_lista=list.id JOIN usuario as us ON us.id=sub.id_usuario where us.id=?1",
	nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionSubscritas(int id_usuario);//
	@Query(value="SELECT * FROM lista as list JOIN subcripciones as sub ON sub.id_lista=list.id JOIN usuario as us ON us.id=sub.id_usuario where us.id=?1 and list.nombre LIKE ?2%",
			nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionSubscritasByNombre(int id_usuario,String nombre);//
	@Query(value="SELECT * FROM lista WHERE id_usuario != ?1",
			nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionNoUsusario(int id_usuario);//
	@Query(value="SELECT * FROM lista WHERE id_usuario != ?1 AND nombre LIKE ?2%",
			nativeQuery = true)
	List<ListaReproduccion> getAllListasReproduccionNoUsusarioByNombre(int id_usuario, String nombre);//
	@Query(value="INSERT INTO subcripciones(id_usuario,id_lista) VALUES(?1,?2) RETURNING id_usuario",
			nativeQuery = true)
	int SubscribirseAListaReproduccion(int id_usuario,int id_lista);//
	@Query(value="DELETE FROM subcripciones WHERE id_usuario=?1 AND id_lista=?2 RETURNING id_usuario",
			nativeQuery = true)
	int DesubscribirssListaReproduccion(int id_usuario,int id_lista);//
	@Query(value="INSERT INTO lista_cancion(id_lista,id_cancion) VALUES(?1,?2) RETURNING id_lista",
			nativeQuery = true)
	int AddCancionAListaReproduccion(int id_lista,int id_cancion);//
	@Query(value="DELETE FROM lista_cancion WHERE id_lista=?1 AND id_cancion=?2 RETURNING id_lista",
			nativeQuery = true)
	int DeleteCancionListaReproduccion(int id_usuario,int id_lista);//
	@Query(value="SELECT COUNT(*) FROM subcripciones WHERE id_lista=?1 AND id_usuario=?2",
			nativeQuery = true)
	int existeSubcripcion(int id_lista,int id_usuario);//
	@Query(value="SELECT COUNT(*) FROM lista_cancion WHERE id_lista=?1 AND id_cancion=?2",
			nativeQuery = true)
	int existeCancionEnLAListaReproduccion(int id_lista,int id_cancion);//
}
