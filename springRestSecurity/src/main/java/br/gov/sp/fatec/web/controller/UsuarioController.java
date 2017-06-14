package br.gov.sp.fatec.web.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.service.AutorizacaoService;
import br.gov.sp.fatec.service.UsuarioService;
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UsuarioController {
	    @Autowired
	    @Qualifier("authenticationManager")
	    private AuthenticationManager auth;
	    
//	    @Autowired
//	    private SegurancaServiceImpl user;
	    
	    public void setAuth(AuthenticationManager auth) {
	        this.auth = auth;
	    }
	    
	    @Autowired
	    private UsuarioService user;
	    public void setUsuarioServce(UsuarioService usuarioservice){
	    	this.user = usuarioservice;
	    }
	    
		@Autowired
		private AutorizacaoService autorizacaoService;

		public void setAutorizacaoService(AutorizacaoService autorizacaoService) {
			this.autorizacaoService = autorizacaoService;
		}
	    
	    @RequestMapping(path = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    public void newUser(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
	    	
	    	if( json.get("cpf") == null || json.get("username") == null|| json.get("password") == null){
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
	    	}else{
	    		Usuario usuario = new Usuario();
	    		usuario.setCPF(json.get("cpf"));
	    		usuario.setNome(json.get("username"));
	    		if(json.get("role") == null || json.get("role").equals("")){
	    			System.out.println("here");
	    			usuario.setAutorizacoes(autorizacaoService.buscar("ROLE_USER"));
	    		}else{
	    			usuario.setAutorizacoes(autorizacaoService.buscar("ROLE_ADMIN"));
	    		}
	    	    MessageDigest md = MessageDigest.getInstance("MD5");
	    	    md.update(json.get("password").getBytes("UTF-8"));
	    	    byte[] digest = md.digest();
	    	    usuario.setSenha(DatatypeConverter
	    	      .printHexBinary(digest).toLowerCase());
	    	    try{
	    	    	user.save(usuario);
	    	    	response.setStatus(HttpServletResponse.SC_OK);
	    	    }catch(DataIntegrityViolationException e){
	    	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome ou Senha já existente no Banco de dados.");
	    	    }
	    	}
	    }
	    
	    @RequestMapping(path = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    @PreAuthorize("isAuthenticated()")
	    public void updateUser(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
	    	
	    	if(json.get("id") == null ||json.get("cpf") == null || json.get("nome") == null){
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
	    	}else{
	    		Usuario usuario = new Usuario();
	    		usuario.setId(Long.valueOf(json.get("id")));
	    		usuario.setCPF(json.get("cpf"));
	    		usuario.setNome(json.get("nome"));
	    		usuario.setAutorizacoes(autorizacaoService.buscar("ROLE_USER"));
	    	    MessageDigest md = MessageDigest.getInstance("MD5");
	    	    if(json.get("senha") != null && json.get("senha")!= ""){
	    	    	md.update(json.get("senha").getBytes("UTF-8"));
		    	    byte[] digest = md.digest();
		    	    usuario.setSenha(DatatypeConverter
		    	      .printHexBinary(digest).toLowerCase());
	    	    }
	    	    
	    	    try{
	    	    	user.save(usuario);
	    	    	response.setStatus(HttpServletResponse.SC_OK);
	    	    }catch(DataIntegrityViolationException e){
	    	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Inválidos.");
	    	    }
	    	}
	    }
	    
	    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	    @PreAuthorize("isAuthenticated()")
	    public void deleteUser(@PathVariable("id") int userId, HttpServletResponse response) throws IOException{
	    	Usuario usuario = user.getById(Long.valueOf(userId));
	    	if(usuario != null){
		    	try{
		    		user.delete(usuario);
		    		response.setStatus(HttpServletResponse.SC_OK);
		    	}catch(DataIntegrityViolationException e){
		    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ocorreu um erro durante o processo.");
		    	}
	    	}else{
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuário não encontrado na Base de Dados.");
	    	}
	    }
	    
}
