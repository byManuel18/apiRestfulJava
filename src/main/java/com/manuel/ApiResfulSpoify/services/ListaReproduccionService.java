package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Cancion;
import com.manuel.ApiResfulSpoify.models.ListaReproduccion;
import com.manuel.ApiResfulSpoify.repositories.CancionRepository;
import com.manuel.ApiResfulSpoify.repositories.ListaReproducciónRepository;

@Service
public class ListaReproduccionService {

	@Autowired
	ListaReproducciónRepository listareproduccionRepository;
	@Autowired
	CancionRepository cancionRepository;

	public List<ListaReproduccion> getAllListaseproduccion() {

		List<ListaReproduccion> listasreproduccion = listareproduccionRepository.findAll();

		if (listasreproduccion.size() > 0) {
			return listasreproduccion;
		} else {
			return new ArrayList<ListaReproduccion>();
		}

	}

	public ListaReproduccion getListaReproduccionById(int id) {
		Optional<ListaReproduccion> listareproduccion = listareproduccionRepository.findById(id);
		if (listareproduccion.isPresent()) {
			return listareproduccion.get();
		} else {
			throw new RecordNotFoundException("No PlayList record exist for given id", id);
		}
	}

	public ListaReproduccion createListaReproduccion(ListaReproduccion lr) {
		if(existeListaProduccionEnUsuario(lr.getCreador().getId(),lr.getNombre().toUpperCase())) {
			throw new ExistingObjectException("Existing PlayList "+ lr.getNombre()+" for User: "+lr.getCreador().getId());
		}
		ListaReproduccion nuevalistare = new ListaReproduccion();
		nuevalistare.setCanciones(lr.getCanciones());
		nuevalistare.setCreador(lr.getCreador());
		nuevalistare.setDescripcion(lr.getDescripcion());
		nuevalistare.setNombre(lr.getNombre().toUpperCase());
		nuevalistare.setUsuarios_subcritos(lr.getUsuarios_subcritos());
		return listareproduccionRepository.save(nuevalistare);
	}

	public ListaReproduccion updateListaReproduccion(ListaReproduccion lr) {
		if (lr.getId() > 0) {
			if(existeListaProduccionEnUsuario(lr.getCreador().getId(),lr.getNombre().toUpperCase())) {
				throw new ExistingObjectException("Existing PlayList "+ lr.getNombre()+" for User: "+lr.getCreador().getId());
			}
			Optional<ListaReproduccion> lista = listareproduccionRepository.findById(lr.getId());
			if (lista.isPresent()) {
				ListaReproduccion to_update = lista.get();
				to_update.setCanciones(lr.getCanciones());
				to_update.setCreador(lr.getCreador());
				to_update.setDescripcion(lr.getDescripcion());
				to_update.setNombre(lr.getNombre().toUpperCase());
				to_update.setUsuarios_subcritos(lr.getUsuarios_subcritos());
				to_update.setId(lr.getId());
				return listareproduccionRepository.save(to_update);
			} else {
				throw new RecordNotFoundException("Playlist not found", lr.getId());
			}
		} else {
			throw new RecordNotFoundException("Not id of playlist given", lr.getId());
		}
	}

	public void deleteListaReproduccion(int id) {
		Optional<ListaReproduccion> todelete = listareproduccionRepository.findById(id);
		if (todelete.isPresent()) {
			listareproduccionRepository.delete(todelete.get());
		} else {
			throw new RecordNotFoundException("PlayList not found", id);
		}
	}

	public int subscribirseListaReproduccion(int id_usuario, int id_lista) {
		Optional<ListaReproduccion> lista=listareproduccionRepository.findById(id_lista);
		if(lista.get().getCreador().getId()==id_usuario) {
			return -1;
		}
		int cout = listareproduccionRepository.existeSubcripcion(id_lista, id_usuario);
		if (cout == 0) {
			return listareproduccionRepository.SubscribirseAListaReproduccion(id_usuario, id_lista);
		} else {
			return -1;
		}

	}

	public int desubscriberseListaReproduccion(int id_usuario, int id_lista) {

		int cout = listareproduccionRepository.existeSubcripcion(id_lista, id_usuario);
		if (cout > 0) {
			return listareproduccionRepository.DesubscribirssListaReproduccion(id_usuario, id_lista);
		} else {
			return -1;
		}
	}

	public int existeSubscripcion(int id_lista, int id_usuario) {
		return listareproduccionRepository.existeSubcripcion(id_lista, id_usuario);

	}

	public List<ListaReproduccion> getListasReproduccionByCreador(int id_usuario) {

		List<ListaReproduccion> listasReproduccion = listareproduccionRepository
				.getAllListasReproduccionCreadasByUsuario(id_usuario);

		if (listasReproduccion.size() > 0) {
			return listasReproduccion;
		} else {
			return new ArrayList<ListaReproduccion>();
		}
	}

	public List<ListaReproduccion> getListasReproduccionByCreadorAndNombre(int id_usuario, String nombre) {

		List<ListaReproduccion> listasReproduccion = listareproduccionRepository
				.getAllListasReproduccionCreadasByUsuarioAndNombre(id_usuario, nombre.toUpperCase());

		if (listasReproduccion.size() > 0) {
			return listasReproduccion;
		} else {
			return new ArrayList<ListaReproduccion>();
		}
	}

	public List<ListaReproduccion> getListasReproduccionSubscritasByUsuario(int id_usuario) {

		List<ListaReproduccion> listasReproduccion = listareproduccionRepository
				.getAllListasReproduccionSubscritas(id_usuario);

		if (listasReproduccion.size() > 0) {
			return listasReproduccion;
		} else {
			return new ArrayList<ListaReproduccion>();
		}
	}

	public List<ListaReproduccion> getListasReproduccionSubscritasByUsuarioByNombre(int id_usuario, String nombre) {

		List<ListaReproduccion> listasReproduccion = listareproduccionRepository
				.getAllListasReproduccionSubscritasByNombre(id_usuario, nombre.toUpperCase());

		if (listasReproduccion.size() > 0) {
			return listasReproduccion;
		} else {
			return new ArrayList<ListaReproduccion>();
		}
	}

	public boolean existeListaProduccionEnUsuario(int id_usuario, String nombre) {

		List<ListaReproduccion> listas = listareproduccionRepository.existListaRproduccion(id_usuario,
				nombre.toUpperCase());

		if (listas.size() > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public List<ListaReproduccion> getListasReproduccionParaSubscribirse(int id_usuario){
		List<ListaReproduccion> listas=listareproduccionRepository.getAllListasReproduccionNoUsusario(id_usuario);
		
		if(listas.size()>0) {
			return listas;
		}else {
			return new ArrayList<ListaReproduccion>();
		}
	}
	
	public List<ListaReproduccion> getListasReproduccionParaSubscribirseByName(int id_usuario,String nombre){
		List<ListaReproduccion> listas=listareproduccionRepository.getAllListasReproduccionNoUsusarioByNombre(id_usuario,nombre.toUpperCase());
		
		
		if(listas.size()>0) {
			return listas;
		}else {
			return new ArrayList<ListaReproduccion>();
		}
	}
	
	public boolean existeCancionEnListaReproduccion(int id_lista,int id_cancion) {
		int count=listareproduccionRepository.existeCancionEnLAListaReproduccion(id_lista, id_cancion);
		if(count>0) {
			return true;
		}else{
			return false;
		}
	}
	
	public int AddCancionListaReproduccion(int id_lista,int id_cancion) {
		if(!existeCancionEnListaReproduccion(id_lista, id_cancion)) {
			return listareproduccionRepository.AddCancionAListaReproduccion(id_lista, id_cancion);
		}else {
			return -1;
		}
	}
	
	public int DeleteCancionListaReproduccion(int id_lista,int id_cancion) {
		if(existeCancionEnListaReproduccion(id_lista, id_cancion)) {
			return listareproduccionRepository.DeleteCancionListaReproduccion(id_lista, id_cancion);
		}else {
			return -1;
		}
	}
	
	public List<Cancion> getAllCancionesListaReproduccion(int id_lista){
		
		List<Cancion> listacanciones=cancionRepository.getCancionesByListaReproduccion(id_lista);
	
		if(listacanciones.size()>0) {
			return listacanciones;
		}else {
			return new ArrayList<Cancion>();
		}
	}
}
