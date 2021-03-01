package com.manuel.ApiResfulSpoify.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manuel.ApiResfulSpoify.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@Query(value = "SELECT * FROM usuario WHERE correo = ?1"
			,nativeQuery = true)
	List<Usuario> existeUsuario(String correo);
	
	@Query(value = "SELECT * FROM usuario WHERE nombre LIKE ?1%"
			,nativeQuery = true)
	List<Usuario> getUsuariosByNombre(String nombre);
	
	@Query(value = "SELECT * FROM usuario WHERE correo = ?1"
			,nativeQuery = true)
	List<Usuario> getUsuarioByCorreo(String correo);
	
}
