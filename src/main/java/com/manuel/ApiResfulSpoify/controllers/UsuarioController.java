package com.manuel.ApiResfulSpoify.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Usuario;
import com.manuel.ApiResfulSpoify.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuario")
@Api(tags = "USUARIO")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	@ApiOperation(value = "Obtener todos los Usuarios",notes = "Devuelve una lista con todos los Usuarios de la base de datos")
	public ResponseEntity<List<Usuario>> getAllUsuarios(){
		List<Usuario> listausuarios=usuarioService.getAllUsuarios();
		
		return new ResponseEntity<List<Usuario>>(listausuarios,new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@GetMapping("/search/{nombre}")
	@ApiOperation(value = "Obtener todos los Artistas por nombre",notes = "Devuelve una lista con todos los Usuarios cuyo nombre coincide con la cadena introducida")
	public ResponseEntity<List<Usuario>> getUsuariosByName(@PathVariable("nombre") String nombre){
		List<Usuario> listausuarios=usuarioService.getUsuariosByName(nombre.toUpperCase());
		
		return new ResponseEntity<List<Usuario>>(listausuarios,new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener Usuario por id",notes = "Devuelve un Usuario mediante la id introducida. Lanza excepción si el id no es válido")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") int id) throws RecordNotFoundException{
		Usuario usuario=usuarioService.getUsuarioByIn(id);
		
		return new ResponseEntity<Usuario>(usuario,new HttpHeaders(), HttpStatus.OK);
		
	}
	
	@GetMapping("/correo/{correo}")
	@ApiOperation(value = "Obtener Usuario por correo",notes = "Devuelve un Usuario mediante el correo introducido")
	public ResponseEntity<Usuario> getUsuarioByCorreo(@PathVariable("correo")String correo){
		
		Usuario usuario=usuarioService.getUsuarioByCorreo(correo);
		
		return new ResponseEntity<Usuario>(usuario, new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@GetMapping("/exist/{correo}")
	@ApiOperation(value = "Comprobar si existe un Usuario",notes = "Devuelve true o false si hay un Usaurio con el correo introducido")
	public ResponseEntity<Boolean> existUsuario(@PathVariable("correo") String correo){
		boolean existe=usuarioService.existeUsuario(correo);
		
		return new ResponseEntity<Boolean>(existe,new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Usuario",notes = "Introduce un Usuario mediante un JSON válido devolviendo el Usuario introducido. Lanza excepción si ya hay un Usuario con el mismo correo")
	public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario u) throws ExistingObjectException{
		Usuario created=usuarioService.crearUsuario(u);
		
		return new ResponseEntity<Usuario>(created,new HttpHeaders(),HttpStatus.OK);
		
		
	}
	
	@PutMapping
	@ApiOperation(value = "Actualizar Usuario",notes = "Actualiza un Usuario mediante un JSON válido introducido devolviendo el Usuario actualizado. Lanza excepción si no existe el Usuario o si ya existe un Usuario con el correo a actualizar ")
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario u) throws RecordNotFoundException,ExistingObjectException{
		Usuario updated=usuarioService.updateUsuario(u);
		
		return new ResponseEntity<Usuario>(updated,new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Usuario",notes = "Eliminar un Usuario mediante su id introducida. Lanza excepción si no existe el Usuaro a eliminar")
	public HttpStatus deleteUsuario(@PathVariable("id") int id) throws RecordNotFoundException{
		
		usuarioService.deleteUsuario(id);
		
		return HttpStatus.ACCEPTED;
		
	}
}
