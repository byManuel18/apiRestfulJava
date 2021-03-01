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
import com.manuel.ApiResfulSpoify.models.Cancion;
import com.manuel.ApiResfulSpoify.services.CancionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cancion")
@Api(tags = "CANCIONES")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CancionController {
	
	@Autowired
	CancionService cancionService;
	
	@GetMapping
	@ApiOperation(value = "Obtener todas las Canciones",notes = "Devuelve una lista con todas las Canciones de la base de datos")
	public ResponseEntity<List<Cancion>> getAllCanciones(){
		List<Cancion> listacanciones=cancionService.getAllCanciones();
		
		return new ResponseEntity<List<Cancion>>(listacanciones,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener Cancion por id",notes = "Devuelve una Cancion por el id introducido. Lanza una excepción si no existe")
	public ResponseEntity<Cancion> getCancionById(@PathVariable("id") int id) throws RecordNotFoundException{
		Cancion cancion=cancionService.getCancionById(id);
		return new ResponseEntity<Cancion>(cancion,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/search/{nombre}")
	@ApiOperation(value = "Obtener Canciones por nombre",notes = "Devuelve una lista con todas las Canciones cuyo nombre coincide con el introducido")
	public ResponseEntity<List<Cancion>> getCancionesByNombre(@PathVariable("nombre") String nombre){
		
		List<Cancion> listaCanciones=cancionService.getCancionesByNombre(nombre);
		
		return new ResponseEntity<List<Cancion>>(listaCanciones,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@GetMapping("/exist/{id_disco}/{nombre}")
	@ApiOperation(value = "Comprobar si existe una Canción en un disco",notes = "Devuelve true o false dependiendo de si hay o no una canción en un Disco con el nombre introducido")
	public ResponseEntity<Boolean> existCancion(@PathVariable("id_disco")int id_disco,@PathVariable("nombre") String nombre){
		boolean existe = cancionService.existCancion(id_disco, nombre);
			
		return new ResponseEntity<Boolean>(existe, new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@PutMapping
	@ApiOperation(value = "Actualizar Cancion",notes = "Actualiza una Cancion mediante un JSON válido devolviendo la Canción actualiada. Lanza excepción si no existe o si ya hay una Canción en un Disco con el mismo nombre")
	public ResponseEntity<Cancion> updateCancion(@Valid @RequestBody Cancion c) throws RecordNotFoundException,ExistingObjectException{
		
		Cancion updated=cancionService.updateCancion(c);
		
		return new ResponseEntity<Cancion>(updated,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Canción",notes = "Crear una Canción mediante un JSON válido devolviendo el Disco creado. Lanza excepción si ya hay una Canción en un Disco con el mismo nombre")
	public ResponseEntity<Cancion> createCancion(@Valid @RequestBody Cancion c) throws ExistingObjectException{
		Cancion created=cancionService.createCancion(c);
		
		return new ResponseEntity<Cancion>(created,new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Canción",notes = "Elimanr una Canción mediante su id. Lanza excepción si no existe la Canción a eliminar")
	public HttpStatus deleteCancion(@PathVariable("id") int id)throws RecordNotFoundException {
		cancionService.deleteCancion(id);
		return HttpStatus.ACCEPTED;
	}
}
