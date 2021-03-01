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
import com.manuel.ApiResfulSpoify.models.Cancion;
import com.manuel.ApiResfulSpoify.models.ListaReproduccion;
import com.manuel.ApiResfulSpoify.services.ListaReproduccionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/playlist")
@Api(tags = "LISTAS REPRODUCCION")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ListaReproduccionController {
	
	@Autowired
	ListaReproduccionService listaReproduccionService;
	
	@GetMapping
	@ApiOperation(value = "Obtener todas las Listas de Reproducción",notes = "Devuelve una lista con todas las Listas de Reproducción de la base de datos")
	public ResponseEntity<List<ListaReproduccion>> getAllListasReproduccion(){
		List<ListaReproduccion> listasreproduccion=listaReproduccionService.getAllListaseproduccion();
		
		return new ResponseEntity<List<ListaReproduccion>>(listasreproduccion,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener Lista de Reproducción por id",notes = "Devuelve una Lista de reproducción mediante una id introducida. Lanza excepción si no existe")
	public ResponseEntity<ListaReproduccion> getListaReproduccionById(@PathVariable("id") int id) throws RecordNotFoundException{
		ListaReproduccion listareporoduccion=listaReproduccionService.getListaReproduccionById(id);
		
		return new ResponseEntity<ListaReproduccion>(listareporoduccion,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = "Crear Lista Reproducción",notes = "Insertar Lista Reproducción mediante un JSON válido. Devuelve la Lista de Reproducción creada. Lanza excepción si el Usuario ya tiene una Lista de Reproducción con ese nombre")
	public ResponseEntity<ListaReproduccion> createListaReproduccion(@Valid @RequestBody ListaReproduccion lr) throws ExistingObjectException{
		ListaReproduccion created=listaReproduccionService.createListaReproduccion(lr);
		
		return new ResponseEntity<ListaReproduccion>(created,new HttpHeaders(),HttpStatus.OK);
	}
	
	@PutMapping
	@ApiOperation(value = "Actualizar Lista Reproducción",notes = "actualiza una Lista Reproducción mediante un JSON válido. Devuelve la Lista de Reproducción creada. Lanza excepción si el Usuario ya tiene una Lista de Reproducción con ese nombre o si no existe ")
	public ResponseEntity<ListaReproduccion> updateListaReproduccion(@Valid @RequestBody ListaReproduccion lr) throws ExistingObjectException,RecordNotFoundException{
		ListaReproduccion updated=listaReproduccionService.updateListaReproduccion(lr);
		return new ResponseEntity<ListaReproduccion>(updated,new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Eliminar Lista Reproducción",notes = "Elimina una Lista Reproducción mediante su id. Lanza excepción si no existe")
	public HttpStatus deleteListaReproduccion(@PathVariable("id")int id) {
		listaReproduccionService.deleteListaReproduccion(id);
		return HttpStatus.ACCEPTED;
	}
	@ApiOperation(value = "Subscribirse a una Lista Reproducción",notes = "Subscribirse a una Lista de Reproducción mediante el id de esta y el id del Usuario que se va a subscribir. Devolverá el id del Usuario si se ha subscrito y -1 si no ha podido (fallará si ya estás subscrito o si eres el creador de la Lista -> -1")
	@PostMapping("/{id_lista}/subscribe/{id_usuario}")
	public ResponseEntity<Integer> subscribirseListaReproduccion(@PathVariable("id_lista")int id_lista,@PathVariable("id_usuario") int id_usuario){
		int rowfected=listaReproduccionService.subscribirseListaReproduccion(id_usuario, id_lista);
		
		return new ResponseEntity<Integer>(rowfected,new HttpHeaders(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_lista}/desubs/{id_usuario}")
	@ApiOperation(value = "Desubscribirse de una Lista Reproducción",notes = "desubscribirse de una Lista de Reproducción mediante el id de esta y el id del Usuario que se va a subscribir. Devolverá el id del Usuario si se ha desubscrito correctamente o -1 si no ha podido (fallará si no existe la subscripción)")
	public ResponseEntity<Integer> desubscribirseListaReproduccion(@PathVariable("id_lista")int id_lista,@PathVariable("id_usuario") int id_usuario){
		int rowfected=listaReproduccionService.desubscriberseListaReproduccion(id_usuario, id_lista);
		return new ResponseEntity<Integer>(rowfected,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/exist/subsc/{id_lista}/{id_usuario}")
	@ApiOperation(value = "Existe Subscripción",notes = "Comprueba se un Usuario está subscrito a una Lista de Reproducción mediante el id de esta y el del usuario. Devuelve true o false dependiendo si existe o no")
	public ResponseEntity<Integer> existSubscrip(@PathVariable("id_lista")int id_lista,@PathVariable("id_usuario") int id_usuario){
		int rowfected=listaReproduccionService.existeSubscripcion(id_lista,id_usuario);
		return new ResponseEntity<Integer>(rowfected,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/created/{id_usuario}")
	@ApiOperation(value = "Listar las Listas de Reproducción de un Usuario",notes = "Lista todas las Listas de Reproducción que ha creado un Usuario mediante su id")
	public ResponseEntity<List<ListaReproduccion>> getListasReproduccionByCreador(@PathVariable("id_usuario")int id_usuario){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionByCreador(id_usuario);
		
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}

	@GetMapping("/created/{id_usuario}/search/{nombre}")
	@ApiOperation(value = "Listar Listas Reproducción de un Usuario y nombre",notes = "Deveulve las Listas de Reproducción Creadas por un Usuario y que además el nombre de la lista coincide con el introducido")
	public ResponseEntity<List<ListaReproduccion>> getListasReproduccionByCreadorAndNombre(@PathVariable("id_usuario")int id_usuario,@PathVariable("nombre") String nombre){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionByCreadorAndNombre(id_usuario, nombre);
		
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/subsc/{id_usuario}")
	@ApiOperation(value = "Listar Listas Reproducción subscritas de un Usuario",notes = "Deveulve las Listas de Reproducción subscritas por un Usuario")
	public ResponseEntity<List<ListaReproduccion>> getListasReproduccionSubscritasByUsuario(@PathVariable("id_usuario")int id_usuario){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionSubscritasByUsuario(id_usuario);
		
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/subsc/{id_usuario}/search/{nombre}")
	@ApiOperation(value = "Listar Listas Reproducción subscritas de un Usuario y nombre",notes = "Deveulve las Listas de Reproducción subscritas de un Usuario y que además el nombre de la lista coincide con el introducido")
	public ResponseEntity<List<ListaReproduccion>> getListasReproduccionSubscritasByUsuarioByNombre(@PathVariable("id_usuario")int id_usuario,@PathVariable("nombre") String nombre){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionSubscritasByUsuarioByNombre(id_usuario, nombre);
		
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/exist/{id_usuario}/{nombre}")
	@ApiOperation(value = "Lista de Reproducción existente",notes = "Comprueba si un Usuario tiene una Lista de Reproducción con el nombre introducido")
	public ResponseEntity<Boolean> existListaReproduccionEnUsuario(@PathVariable("nombre")String nombre,@PathVariable("id_usuario") int id_usuario){
		boolean existe=listaReproduccionService.existeListaProduccionEnUsuario(id_usuario,nombre);
		return new ResponseEntity<Boolean>(existe,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/playlisToSubsc/{id_usuario}")
	@ApiOperation(value = "Obtener Listar de Reproducción para subscribirse",notes = "Deveulve las Listas de Reproducción a las que un Usuario puede subscribirse")
	public ResponseEntity<List<ListaReproduccion>> getListasReproducionParaSubscribirse(@PathVariable("id_usuario")int id_usuario){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionParaSubscribirse(id_usuario);
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/playlisToSubsc/{id_usuario}/search/{nombre}")
	@ApiOperation(value = "Obtener Listar de Reproducción para subscribirse y nombre",notes = "Deveulve las Listas de Reproducción a las que un Usuario puede subscribirse y que además coinciden con el nombre introducido")
	public ResponseEntity<List<ListaReproduccion>> getListasReproducionParaSubscribirseByNombre(@PathVariable("id_usuario")int id_usuario,@PathVariable("nombre") String nombre){
		List<ListaReproduccion> listas=listaReproduccionService.getListasReproduccionParaSubscribirseByName(id_usuario,nombre);
		return new ResponseEntity<List<ListaReproduccion>>(listas,new HttpHeaders(),HttpStatus.OK);
	}
	@GetMapping("/existSongOnPlayList/{id_lista}/{id_cancion}")
	@ApiOperation(value = "Existe canción en Lista de Reproducción",notes = "Comprueba si una Lista de reproducción existe en un Usuario dependiendo de su nombre. Devuelve true o false dependiendo de si existe o no")
	public ResponseEntity<Boolean> existeCancionEnListaReproduccion(@PathVariable("id_lista") int id_lista,@PathVariable("id_cancion") int cancion){
		
		boolean existe=listaReproduccionService.existeCancionEnListaReproduccion(id_lista, cancion);
		
		return new ResponseEntity<Boolean>(existe,new HttpHeaders(),HttpStatus.OK);
		
	}
	
	@PostMapping("/{id_lista}/addSong/{id_cancion}")
	@ApiOperation(value = "Añadir Canción a la Lista de Reproducción",notes = "Añade una cancion a una Lista de Reproducción devolviendo el id de la lista a la que se ha introducido si se ha podido ejecutar, Devuelve -1 en caso contrario. (si existe ya en la lista devuelve -1)")
	public ResponseEntity<Integer> addCancionPlayList(@PathVariable("id_lista") int id_lista,@PathVariable("id_cancion") int id_cancion){
		int rows=listaReproduccionService.AddCancionListaReproduccion(id_lista, id_cancion);
		
		return new ResponseEntity<Integer>(rows,new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_lista}/deleteSong/{id_cancion}")
	@ApiOperation(value = "Elimina Canción a la Lista de Reproducción",notes = "Elimina una cancion de la Lista de Reproducción devolviendo el id de la lista de la que se ha eliminado si se ha podido ejecutar, Devuelve -1 en caso contrario. (si no existe en la lista devuelve -1)")
	public ResponseEntity<Integer> deleteCancionPlayList(@PathVariable("id_lista") int id_lista,@PathVariable("id_cancion") int id_cancion){
		int rows=listaReproduccionService.AddCancionListaReproduccion(id_lista, id_cancion);
		
		return new ResponseEntity<Integer>(rows,new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id_lista}/SongsList")
	@ApiOperation(value = "Mostrar canciones de una Lista de reproducción",notes = "Muesta todas las canciones que hay en una Lista de Reproducción pasandole el id de esta")
	public ResponseEntity<List<Cancion>> getCancionesListaReproduccion(@PathVariable("id_lista")int id_lista){
		List<Cancion> listacanciones=listaReproduccionService.getAllCancionesListaReproduccion(id_lista);
		
		return new ResponseEntity<List<Cancion>>(listacanciones,new HttpHeaders(),HttpStatus.OK);
	}
}
