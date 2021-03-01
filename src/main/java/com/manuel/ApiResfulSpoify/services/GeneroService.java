package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.models.Genero;
import com.manuel.ApiResfulSpoify.repositories.GeneroRepository;
import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;

@Service
public class GeneroService {
	@Autowired
	GeneroRepository generoRepository;
	
	public List<Genero> getAllGeneros(){
		
		List<Genero> generoslist= generoRepository.findAll();
		
		if(generoslist.size()>0) {
			return generoslist;
		}else {
			return new ArrayList<Genero>();
		}
		
	}
	
	public Genero getGeneroById(int id) {
		Optional<Genero> genero=generoRepository.findById(id);
		if(genero.isPresent()) {
			return genero.get();
		}else {
			throw new RecordNotFoundException("No Genre record exist for given id", id);
		}
		
	}
	
	public Genero crearGenero(Genero g) {
		if(existeGenero(g.getNombre())) {
			throw new ExistingObjectException("Existing Genre: "+g.getNombre());
		}
		g.setNombre(g.getNombre().toUpperCase());
		return generoRepository.save(g);
	}
	
	public Genero updateGenero(Genero g) {
		if(existeGenero(g.getNombre())) {
			throw new ExistingObjectException("Existing Genre: "+g.getNombre());
		}
		if(g.getId()>0) {
			Optional<Genero> genro_toupdate=generoRepository.findById(g.getId());
			if(genro_toupdate.isPresent()) {
				Genero g_update=genro_toupdate.get();
				g_update.setNombre(g.getNombre().toUpperCase());
				return generoRepository.save(g_update);
			}else {
				throw new RecordNotFoundException("Genre not found", g.getId());
			}
		}else {
			throw new RecordNotFoundException("Not id of genre given",g.getId());
		}
		
		
	}
	
	public void deleteGenero(int id) {
		Optional<Genero> to_delete=generoRepository.findById(id);
		if(to_delete.isPresent()) {
			generoRepository.delete(to_delete.get());
		}else {
			throw new RecordNotFoundException("Genre not found", id);
		}
	}
	
	public List<Genero> getGenerosByNombre(String nombre){
		List<Genero> listageneros=generoRepository.getGenerosByNombre(nombre.toUpperCase());
		
		if(listageneros.size()>0) {
			return listageneros;
		}else {
			return new ArrayList<Genero>();
		}
	}
	
	public boolean existeGenero(String nombre) {
		List<Genero> generosexistentes=generoRepository.generoExistente(nombre.toUpperCase());
		if(generosexistentes.size()>0){
			return true;
		}else {
			return false;
		}
	}
}
