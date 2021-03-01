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

import com.manuel.ApiResfulSpoify.models.Genero;
import com.manuel.ApiResfulSpoify.services.GeneroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;

@RestController
@RequestMapping("/genero")
@Api(tags = "GÉNERO")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GeneroController {
	
	@Autowired
	GeneroService generoService;
	
	
	@GetMapping
	@ApiOperation(value = "Obtener todos los Géneros",notes = "Devuelve una lista con todos los géneros de la base de datos")
	public ResponseEntity<List<Genero>> getAllGeneros(){
		
		List<Genero> listageneros=generoService.getAllGeneros();
		
		return new ResponseEntity<List<Genero>>(listageneros,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener Género por su id",notes = "Devuelve una género (si existe) mediante su id introducido")
	public ResponseEntity<Genero> getGeneroById(@PathVariable("id") int id)  throws RecordNotFoundException{
		Genero genero=generoService.getGeneroById(id);
		return new ResponseEntity<Genero>(genero,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/exist/{nombre}")
	@ApiOperation(value = "Existe Género",notes = "Mediante un nombre nos devuelve un true o false si existe o no un género con ese nombre")
	public ResponseEntity<Boolean> existeGenero(@PathVariable("nombre") String nombre){
		boolean  existe=generoService.existeGenero(nombre);
		return new ResponseEntity<Boolean>(existe,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/search/{nombre}")
	@ApiOperation(value = "Obtener lista de géneros por nombre",notes = "Devuelve una lista de  géneros mediante una cadena (nombre) con todas las coincidencias")
	public ResponseEntity<List<Genero>> getGenerosByNombre(@PathVariable("nombre") String nombre){
		List<Genero> listageneros=generoService.getGenerosByNombre(nombre);
		
		return new ResponseEntity<List<Genero>>(listageneros,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Género",notes = "Introduce un género nuevo mediante un JSON válido y devuelve el género si se ha podido introducir. Lanza excepción si"
			+ "ya existe el génro en la base de datos")
	public ResponseEntity<Genero> createGenero(@Valid @RequestBody Genero genero){
		Genero g_aded=generoService.crearGenero(genero);
		
		return new ResponseEntity<Genero>(g_aded,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping
	@ApiOperation(value = "Actualizar género",notes = "Actualiza un género a partir de un JSON válidon y devuelve el género actualizado. Si no existe devulve una excepción y "
			+ "si ya existe devuelve otra excepción")
	public ResponseEntity<Genero> updateGenero(@Valid @RequestBody Genero genero) throws RecordNotFoundException{
		Genero g_updated=generoService.updateGenero(genero);
		
		return new ResponseEntity<Genero>(g_updated, new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Género",notes = "Elimina un génereo a partir de su id. Lanza excepción si la id no es válida")
	public HttpStatus deleteGenero(@PathVariable("id") int id) throws RecordNotFoundException{
		generoService.deleteGenero(id);
		return HttpStatus.ACCEPTED;
	}
}
