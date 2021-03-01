package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Artista;
import com.manuel.ApiResfulSpoify.models.Disco;
import com.manuel.ApiResfulSpoify.repositories.ArtistaRepository;
import com.manuel.ApiResfulSpoify.repositories.DiscoRepository;

@Service
public class ArtistaService {
	
	@Autowired
	ArtistaRepository artistaRepository;
	@Autowired
	DiscoRepository discoRepository;
	
	public List<Artista> getAllArtistas(){
		List<Artista> listaArtistas=artistaRepository.findAll();
		
		if(listaArtistas.size()>0) {
			return listaArtistas;
		}else {
			return new ArrayList<Artista>();
		}
	}
	
	public Artista getArtistaById(int id){
		Optional<Artista> artista=artistaRepository.findById(id);
		
		if(artista.isPresent()) {
			return artista.get();
		}else {
			throw new RecordNotFoundException("No Artista record exist for given id", id);
		}
	}
	
	public List<Artista> getArtistasByNombre(String nombre) {
		List<Artista> listaartistas=artistaRepository.getAllArtistasByNombre(nombre.toUpperCase());
		
		if(listaartistas.size()>0) {
			return listaartistas;
		}else {
			return new ArrayList<Artista>();
		}
	}
	
	public List<Artista> getArtistasByNacionalidad(String nacionalidad) {
		List<Artista> listaartistas=artistaRepository.getAllArtistasByNacionalidad(nacionalidad.toUpperCase());
		
		if(listaartistas.size()>0) {
			return listaartistas;
		}else {
			return new ArrayList<Artista>();
		}
	}
	
	public List<Disco> getDiscosByArtista(int id_artista){
		List<Disco> listaDiscos=discoRepository.getDiscosByArtista(id_artista);
		if(listaDiscos.size()>0) {
			return listaDiscos;
		}else {
			return new ArrayList<Disco>();
		}
	}
	
	public boolean existArtistaByNombre(String name) {
		List<Artista> listaArtistas=artistaRepository.existartista(name.toUpperCase());
		if(listaArtistas.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public Artista createArtista(Artista a) {
		if(existArtistaByNombre(a.getNombre())) {
			throw new ExistingObjectException("Existing Artis: "+a.getNombre());
		}
		Artista nuevo=new Artista();
		nuevo.setFoto(a.getFoto());
		nuevo.setListaDiscos(a.getListaDiscos());
		nuevo.setNacionalidad(a.getNacionalidad().toUpperCase());
		nuevo.setNombre(a.getNombre().toUpperCase());
		return artistaRepository.save(nuevo);
	}
	
	public Artista updateArtista(Artista a) {
		
		if(a.getId()>0){
			if(existArtistaByNombre(a.getNombre())) {
				throw new ExistingObjectException("Existing Artis: "+a.getNombre());
			}
			Optional<Artista> existe=artistaRepository.findById(a.getId());
			if(existe.isPresent()) {
				Artista to_update=existe.get();
				to_update.setFoto(a.getFoto());
				to_update.setNombre(a.getNombre().toUpperCase());
				to_update.setNacionalidad(a.getNacionalidad().toUpperCase());
				to_update.setListaDiscos(a.getListaDiscos());
				return artistaRepository.save(to_update);
			}else {
				throw new RecordNotFoundException("Artist not found", a.getId());
			}
		}else {
			throw new RecordNotFoundException("Not id of artist give", a.getId());
		}
	}
	
	public void deleteArtista(int id) {
		Optional<Artista> to_delete=artistaRepository.findById(id);
		if(to_delete.isPresent()){
			artistaRepository.delete(to_delete.get());
		}else {
			throw new RecordNotFoundException("Genre not found", id);
		}
	}
}
