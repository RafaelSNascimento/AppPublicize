package br.gov.sp.fatec.web.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.model.Empresa;
import br.gov.sp.fatec.service.EmpresaService;
@CrossOrigin
@RestController
@RequestMapping(value = "/empresa")
public class EmpresaController {

	    @Autowired
	    private EmpresaService emp;
	    public void setEmpresaServce(EmpresaService empresa){
	    	this.emp = empresa;
	    }

	    @RequestMapping(path = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public Empresa newUser(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
	    	Empresa empFinal = new Empresa();
	    	if( json.get("razaoSocial") == null || json.get("nome") == null){
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
	    	}else{
	    		Empresa empresa = new Empresa();
	    		empresa.setRazaoSocial(json.get("razaoSocial"));
	    		empresa.setNome(json.get("nome"));
	    	    try{
	    	    	empFinal = emp.save(empresa, SecurityContextHolder.getContext().getAuthentication().getName());
	    	    	
	    	    	//response.setStatus(HttpServletResponse.SC_OK);
	    	    	return empFinal;
	    	    }catch(DataIntegrityViolationException e){
	    	    	System.out.println(e);
	    	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome ou Razao já existente no Banco de dados.");
	    	    }
	    	}
	    	return null;
	    }
	    
	    @RequestMapping(path = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public void updateUser(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
	    	System.out.println(json.get("id"));
	    	System.out.println(json.get("razaoSocial"));
	    	if(json.get("id") == null || json.get("razaoSocial") == null || json.get("nome") == null || json.get("id").equals("")||
	    	json.get("razaoSocial").equals("") || json.get("nome").equals("")){
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
	    	}else{
	    		Empresa empresa = new Empresa();
	    		empresa.setId(Integer.valueOf(json.get("id")));
	    		empresa.setRazaoSocial(json.get("razaoSocial"));
	    		empresa.setNome(json.get("nome"));
	    	    try{
	    	    	emp.update(empresa);
	    	    	response.setStatus(HttpServletResponse.SC_OK);
	    	    }catch(DataIntegrityViolationException e){
	    	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Inválidos.");
	    	    }
	    	}
	    }
	    
	    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public void deleteUser(@PathVariable("id") int empId, HttpServletResponse response) throws IOException{
	    	Empresa empresa = emp.getById(Integer.valueOf(empId));
	    	if(empresa != null){
		    	try{
		    		emp.delete(empresa);
		    		response.setStatus(HttpServletResponse.SC_OK);
		    	}catch(DataIntegrityViolationException e){
		    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ocorreu um erro durante o processo.");
		    	}
	    	}else{
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Empresa não encontrada na Base de Dados.");
	    	}
	    }
		    

}
