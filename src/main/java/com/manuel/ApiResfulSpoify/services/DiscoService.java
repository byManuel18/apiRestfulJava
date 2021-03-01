package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Cancion;
import com.manuel.ApiResfulSpoify.models.Disco;
import com.manuel.ApiResfulSpoify.repositories.CancionRepository;
import com.manuel.ApiResfulSpoify.repositories.DiscoRepository;

@Service
public class DiscoService {
	@Autowired
	DiscoRepository discoRepository;
	@Autowired
	CancionRepository cancionRepository;
	
	public List<Disco> getAllDiscos(){
		List<Disco> listaDiscos=discoRepository.findAll();
		
		if(listaDiscos.size()>0) {
			return listaDiscos;
		}else {
			return new ArrayList<Disco>();
		}
	}
	
	public Disco getDiscoById(int id) {
		
			Optional<Disco> disco=discoRepository.findById(id);
			if(disco.isPresent()) {
				return disco.get();
			}else {
				throw new RecordNotFoundException("No Disco record exist for given id", id);
			}
	
	}
	
	public Disco createDisco(Disco d) {
		if(existDisco(d.getArtista().getId(),d.getNombre())) {
			throw new ExistingObjectException("Existing Disc "+d.getNombre()+" for Artista: "+d.getArtista().getId());
		}
		Disco nuevo=new Disco();
		nuevo.setArtista(d.getArtista());
		nuevo.setFecha_produccion(d.getFecha_produccion());
		nuevo.setFoto(d.getFoto());
		nuevo.setListacanciones(d.getListacanciones());
		nuevo.setNombre(d.getNombre().toUpperCase());
		return discoRepository.save(nuevo);
	}
	
	public Disco updateDisco(Disco d) {
		
		if(d.getId()>0) {
			if(existDisco(d.getArtista().getId(),d.getNombre())) {
				throw new ExistingObjectException("Existing Disc "+d.getNombre()+" for Artista: "+d.getArtista().getId());
			}
			Optional<Disco> disco=discoRepository.findById(d.getId());
			if(disco.isPresent()) {
				Disco to_update=disco.get();
				to_update.setArtista(d.getArtista());
				to_update.setId(d.getId());
				to_update.setFecha_produccion(d.getFecha_produccion());
				to_update.setFoto(d.getFoto());
				to_update.setListacanciones(d.getListacanciones());
				to_update.setNombre(d.getNombre().toUpperCase());
				return discoRepository.save(to_update);
			}else {
				throw new RecordNotFoundException("Disc not found", d.getId());
			}
		}else {
			throw new RecordNotFoundException("Not id of disc given", d.getId());
		}
		
	}
	
	public void deleteDisco(int id) {
		Optional<Disco> disco_todelete=discoRepository.findById(id);
		if(disco_todelete.isPresent()){
			discoRepository.delete(disco_todelete.get());
		}else {
			throw new RecordNotFoundException("Disc not found", id); 
		}
	}
	
	public boolean existDisco(int id_artista,String nombre) {
		List<Disco> listaDiscos=discoRepository.existDisco(id_artista, nombre.toUpperCase());
		if(listaDiscos.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Disco> getDiscosByNombre(String nombre){
		List<Disco> listaDiscos=discoRepository.getDiscosByNombre(nombre.toUpperCase());
		if(listaDiscos.size()>0) {
			return listaDiscos;
		}else {
			return new ArrayList<Disco>();
		}
	}

	public List<Cancion> getCancionesByDisc(int id_disco) {
		List<Cancion> listacanciones=cancionRepository.getCancionesByDisco(id_disco);
		if(listacanciones.size()>0) {
			return listacanciones;
		}else {
			return new ArrayList<Cancion>();
		}
	}
}
