package com.manuel.ApiResfulSpoify.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Artista;
import com.manuel.ApiResfulSpoify.models.Disco;
import com.manuel.ApiResfulSpoify.services.ArtistaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/artista")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = "ARTISTA")
public class ArtistaController {
	
	@Autowired
	ArtistaService artistaService;
	
	@GetMapping
	@ApiOperation(value = "Obtener todos los Artistas",notes = "Devuelve una lista con todos los Artistas de la base de datos")
	public ResponseEntity<List<Artista>> getAllArtista(){
		List<Artista> listaArtistas=artistaService.getAllArtistas();
		
		return new ResponseEntity<List<Artista>>(listaArtistas,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener un Artista por id",notes = "Devuelve una artista introduciendo su id.Devuelve excepción si no existe")
	public ResponseEntity<Artista> getArtistaById(@PathVariable("id") int id)throws RecordNotFoundException{
		Artista artista=artistaService.getArtistaById(id);
		
		return new ResponseEntity<Artista>(artista, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/search/name/{nombre}")
	@ApiOperation(value = "Obtener todos los Artistas por nombre",notes = "Devuelve una lista con todos los Autores cuyo nombre coincida con la cadena introducida por"
			+ " parámetros")
	public ResponseEntity<List<Artista>> getArtistasByNombre(@PathVariable("nombre") String nombre){
		List<Artista> listaArtistas=artistaService.getArtistasByNombre(nombre.toUpperCase());
		
		return new ResponseEntity<List<Artista>>(listaArtistas, new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/search/nacionality/{nacionalidad}")
	@ApiOperation(value = "Obtener todos los Artistas por nacionalidad",notes = "Devuelve una lista con todos los Autores cuya nacionalidad coincida con la cadena introducida por"
			+ " parámetros")
	public ResponseEntity<List<Artista>> getArtistasByNacionalidad(@PathVariable("nacionalidad") String nacionalidad){
		List<Artista> listaArtistas=artistaService.getArtistasByNacionalidad(nacionalidad.toUpperCase());
		
		return new ResponseEntity<List<Artista>>(listaArtistas, new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/discs")
	@ApiOperation(value = "Obtener todos los discos de un Artista",notes = "Devuelve una lista con todos los discos de un artista mediante el id introducido por parámetros"
			+ "parámetros")
	public ResponseEntity<List<Disco>> getDiscosByArtista(@PathVariable("id") int id){
		List<Disco> listaDiscos=artistaService.getDiscosByArtista(id);
		
		return new ResponseEntity<List<Disco>>(listaDiscos,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/exist/{nombre}")
	@ApiOperation(value = "Existe Artista",notes="Devuelve true o false dependiendo de si existe un Artista cuyo nombre coincide con la cadena introducida")
	public ResponseEntity<Boolean> existArtista(@PathVariable("nombre") String nombre){
		boolean existe=artistaService.existArtistaByNombre(nombre.toUpperCase());
		return new ResponseEntity<Boolean>(existe, new HttpHeaders(),HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear un Artista",notes = "Crea un Artista introduciendo uno válido por parámetros y devolviendo el Artista creado. Devuelve excepción si ya existe")
	public ResponseEntity<Artista> createArtista(@Valid @RequestBody Artista a) throws ExistingObjectException{
		Artista created=artistaService.createArtista(a);
		
		return new ResponseEntity<Artista>(created,new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping
	@ApiOperation(value = "Actualizar Artista",notes = "Actualiza un Artista mediante uno válido introducido por parámetros, devolviendo el artista actualizado. decuelce excepcion si no existe el"
			+ "Autor a actualizar o si ya existe un Artista con el nombre a actualizar")
	public ResponseEntity<Artista> updateArtista(@Valid @RequestBody Artista a)throws ExistingObjectException,RecordNotFoundException{
		Artista updated=artistaService.updateArtista(a);
		
		return new ResponseEntity<Artista>(updated,new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Artista",notes = "Elimina un artista mediante su id introducida por parámetros. Lanza excepción si no existe el Artista a eliminar")
	public HttpStatus deleteArtista(@PathVariable("id") int id) throws RecordNotFoundException {
		artistaService.deleteArtista(id);
		return HttpStatus.ACCEPTED;
	}
}
