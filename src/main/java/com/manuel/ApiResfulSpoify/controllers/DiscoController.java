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
import com.manuel.ApiResfulSpoify.models.Disco;
import com.manuel.ApiResfulSpoify.services.DiscoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/disco")
@Api(tags = "DISCOS")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DiscoController {
	
	@Autowired
	DiscoService discoService;
	
	@GetMapping
	@ApiOperation(value = "Obtener todos los Discos",notes = "Devuelve una lista con todos los discos de la base de datos")
	public ResponseEntity<List<Disco>> getAllDiscos(){
		List<Disco> listaDiscos=discoService.getAllDiscos();
		return new ResponseEntity<List<Disco>>(listaDiscos,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener Disco por id",notes = "Devuelve un disco mediante su id introducido por parámetros. Devuelve excepción si no es una id válida")
	public ResponseEntity<Disco> getDiscoById(@PathVariable("id")int id) throws RecordNotFoundException{
		Disco disco=discoService.getDiscoById(id);
		return new ResponseEntity<Disco>(disco,new HttpHeaders(), HttpStatus.OK);
	}
	@GetMapping("/search/{nombre}")
	@ApiOperation(value = "Obtener todos los Discos por nombre",notes = "Devuelve una lista con todos los Discos cuyo nombre coincida con el introdicido")
	public ResponseEntity<List<Disco>> getAllDiscosByNombre(@PathVariable("nombre") String nombre){
		List<Disco> listaDiscos=discoService.getAllDiscos();
		return new ResponseEntity<List<Disco>>(listaDiscos,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/exist/{id_artista}/{nombre}")
	@ApiOperation(value = "Comprobar si existe un Disco en un Artista",notes = "Devuelve true o false si hay un Disco con el nombre introducido en un Artista introducido mediante si id")
	public ResponseEntity<Boolean> existDisco(@PathVariable("id_artista")int id, @PathVariable("nombre") String nombre){
		boolean existe=discoService.existDisco(id, nombre);
		
		return new ResponseEntity<Boolean>(existe,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/{id}/songs")
	@ApiOperation(value = "Listar las Canciones de un Disco",notes = "Devuelve una lista con todas las Canciones de un Disco mediante su id")
	public ResponseEntity<List<Cancion>> getCancionesByDisco(@PathVariable("id")int id){
		List<Cancion> listaCanciones=discoService.getCancionesByDisc(id);
		
		return new ResponseEntity<List<Cancion>>(listaCanciones, new HttpHeaders(),HttpStatus.OK);
	}
	@PostMapping
	@ApiOperation(value = "Crear nuevo Disco",notes = "Inserta un nuevo Disco válido introducido por JSON devolviendo el Disco introducido. Lanza excepción si ya existe Dico con ese nombre en el Artista")
	public ResponseEntity<Disco> createDisco(@Valid @RequestBody Disco d)throws ExistingObjectException{
		Disco created=discoService.createDisco(d);
		return new ResponseEntity<Disco>(created,new HttpHeaders(),HttpStatus.OK);
	}
	@PutMapping
	@ApiOperation(value = "Actualizar Disco",notes = "Actualiza un Dsico mediante un JSON válido devolviendo el Disco actualizado. Lanza excepción si el Disco no existe o si ya existe un Disco con el nombre a actualizar")
	public ResponseEntity<Disco> updateDisco(@Valid @RequestBody Disco d) throws RecordNotFoundException,ExistingObjectException{
		Disco updated=discoService.updateDisco(d);
		return new ResponseEntity<Disco>(updated,new HttpHeaders(), HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Disco",notes = "Elimina un Disco mediante su id. Lanza excepción si no existe el Disco")
	public HttpStatus deleteDisc(@PathVariable("id") int id) throws RecordNotFoundException{
		discoService.deleteDisco(id);
		return HttpStatus.ACCEPTED;
	}
	
}
