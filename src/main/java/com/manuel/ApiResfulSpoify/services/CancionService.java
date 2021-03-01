package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Cancion;
import com.manuel.ApiResfulSpoify.repositories.CancionRepository;

@Service
public class CancionService {
	
	@Autowired
	CancionRepository cancionRepository;
	
	public List<Cancion> getAllCanciones(){
		List<Cancion> listacanciones=cancionRepository.findAll();
		if(listacanciones.size()>0) {
			return listacanciones;
		}else {
			return new ArrayList<Cancion>();
		}
	}
	
	public Cancion getCancionById(int id) {
		Optional<Cancion> cancion=cancionRepository.findById(id);
		
		if(cancion.isPresent()) {
			return cancion.get();
		}else {
			throw new RecordNotFoundException("No Cancion record exist for given id", id);
		}
	}
	
	public List<Cancion> getCancionesByNombre(String nombre){
		List<Cancion> listaCanciones=cancionRepository.getCancionesByNombre(nombre.toUpperCase());
		if(listaCanciones.size()>0) {
			return listaCanciones;
		}else {
			return new ArrayList<Cancion>();
		}
	}
	
	public boolean existCancion(int id_disco,String nombre) {
		List<Cancion> listaCancion=cancionRepository.existCancion(id_disco, nombre);
		if(listaCancion.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Cancion createCancion(Cancion c) {
		if(existCancion(c.getDisco().getId(),c.getNombre())) {
			 throw new ExistingObjectException("Existing Song "+c.getNombre()+" for Disc: "+c.getDisco().getId());
		}
		Cancion cancion_to_create=new Cancion();
		cancion_to_create.setDisco(c.getDisco());
		cancion_to_create.setDuracion(c.getDuracion());
		cancion_to_create.setGenero(c.getGenero());
		cancion_to_create.setNombre(c.getNombre().toUpperCase());
		return cancionRepository.save(cancion_to_create);
		
	}
	
	public Cancion updateCancion(Cancion c) {
		if(c.getId()>0) {
			if(existCancion(c.getDisco().getId(),c.getNombre())) {
				 throw new ExistingObjectException("Existing Song "+c.getNombre()+" for Disc: "+c.getDisco().getId());
			}
			Optional<Cancion> cancion=cancionRepository.findById(c.getId());
			if(cancion.isPresent()) {
				Cancion to_update=cancion.get();
				to_update.setDisco(c.getDisco());
				to_update.setDuracion(c.getDuracion());
				to_update.setGenero(c.getGenero());
				to_update.setNombre(c.getNombre().toUpperCase());
				to_update.setId(c.getId());
				return cancionRepository.save(to_update);
			}else {
				throw new RecordNotFoundException("Song not found", c.getId());
			}
		}else {
			throw new RecordNotFoundException("Not id of song given", c.getId());
		}
		
	}
	
	public void deleteCancion(int id) {
		Optional<Cancion> to_delete=cancionRepository.findById(id);
		if(to_delete.isPresent()) {
			cancionRepository.delete(to_delete.get());
		}else {
			throw new RecordNotFoundException("Song not found", id);
		}
	}
	 
}
