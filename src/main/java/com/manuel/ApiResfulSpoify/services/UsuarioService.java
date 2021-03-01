package com.manuel.ApiResfulSpoify.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manuel.ApiResfulSpoify.exceptions.ExistingObjectException;
import com.manuel.ApiResfulSpoify.exceptions.RecordNotFoundException;
import com.manuel.ApiResfulSpoify.models.Usuario;
import com.manuel.ApiResfulSpoify.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> getAllUsuarios(){
		List<Usuario> listausuarios=usuarioRepository.findAll();
		if(listausuarios.size()>0) {
			return listausuarios;
		}else {
			return new ArrayList<Usuario>();
		}
		
	}
	
	public Usuario getUsuarioByIn(int id) { 
		
		Optional<Usuario> usuario=usuarioRepository.findById(id);
		
		if(usuario.isPresent()) {
			return  usuario.get();
		}else {
			throw new RecordNotFoundException("No User record exist for given id", id);
		}
		
	}
	
	public Usuario getUsuarioByCorreo(String correo) {
		
		List<Usuario> listausuarios= usuarioRepository.getUsuarioByCorreo(correo);
		if(listausuarios.size()>0) {
			return listausuarios.get(0);
		}else {
			return new Usuario();
		}
	}
	
	public boolean existeUsuario(String correo) {
		List<Usuario> usuario_optenido=usuarioRepository.existeUsuario(correo);
		if(usuario_optenido.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Usuario> getUsuariosByName(String nombre){
		List<Usuario> listausuarios=usuarioRepository.getUsuariosByNombre(nombre.toUpperCase());
		if(listausuarios.size()>0) {
			return listausuarios;
		}else {
			return new ArrayList<Usuario>();
		}
	}
	
	public Usuario crearUsuario(Usuario u) {
		if(existeUsuario(u.getCorreo())) {
			throw new ExistingObjectException("Existing User: "+u.getCorreo());
		}
		Usuario nuevo=new Usuario();
		nuevo.setActivo(u.isActivo());
		nuevo.setCorreo(u.getCorreo());
		nuevo.setFoto(u.getFoto());
		nuevo.setNombre(u.getNombre().toUpperCase());
		nuevo.setListascreadas(u.getListascreadas());
		nuevo.setSubcripciones(u.getSubcripciones());
		nuevo.setContra(u.getContra());
		
		return usuarioRepository.save(nuevo);
		
	}
	
	public Usuario updateUsuario(Usuario u) {
		
		if(u.getId()>0) {
			if(existeUsuario(u.getCorreo())) {
				throw new ExistingObjectException("Existing User: "+u.getCorreo());
			}
			Optional<Usuario> usuario_toupdate=usuarioRepository.findById(u.getId());
			if(usuario_toupdate.isPresent()) {
				Usuario nuevo=usuario_toupdate.get();
				nuevo.setActivo(u.isActivo());
				nuevo.setCorreo(u.getCorreo());
				nuevo.setFoto(u.getFoto());
				nuevo.setId(u.getId());
				nuevo.setNombre(u.getNombre().toUpperCase());
				nuevo.setListascreadas(u.getListascreadas());
				nuevo.setSubcripciones(u.getSubcripciones());
				nuevo.setContra(u.getContra());
				return usuarioRepository.save(nuevo);
			}else {
				throw new RecordNotFoundException("User not found", u.getId());
			}
		}else {
			throw new RecordNotFoundException("No User record exist for given id", u.getId());
		}
		
		
	}
	
	public void deleteUsuario(int id) {
		Optional<Usuario> to_delete=usuarioRepository.findById(id);
		if(to_delete.isPresent()) {
			usuarioRepository.delete(to_delete.get());
		}else {
			throw new RecordNotFoundException("User not found", id);
		}
	}
	
}
