package br.gov.sp.fatec.web.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.model.Empresa;
import br.gov.sp.fatec.model.Evento;
import br.gov.sp.fatec.service.EmpresaService;
import br.gov.sp.fatec.service.EventoService;
@CrossOrigin
@RestController
@RequestMapping(value = "/evento")
public class EventoController {
    @Autowired
    private EventoService evt;
    public void setEventoService(EventoService evt){
    	this.evt = evt;
    }
    
    @Autowired
    private EmpresaService emp;
    public void setEmpresaService(EmpresaService emp){
    	this.emp = emp;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void newEvento(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
    	if(json.get("cidade")== null ||json.get("data") == null || json.get("emp_id")== null || json.get("latitude") == null || json.get("nome") == null || json.get("longitude") == null || json.get("lotacao") == null ||
    		json.get("latitude").equals("") || json.get("data").equals("") || json.get("cidade").equals("") || json.get("emp_id").equals("") || json.get("nome").equals("") || json.get("longitude").equals("") || json.get("lotacao").equals("")){
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
    	}else{
    		Empresa empresa = new Empresa();
    		empresa = emp.getById(Integer.valueOf(json.get("emp_id")));
    		if(empresa != null){
    			Evento evento = new Evento();
        		evento.setLatitude(json.get("latitude"));
        		evento.setLongitude(json.get("longitude"));
        		evento.setLotacao(Integer.valueOf(json.get("lotacao")));
        		evento.setNome(json.get("nome"));
        		evento.setEmpresa(empresa);
        		evento.setCidade(json.get("cidade"));
        		evento.setData(json.get("data"));
        	    try{
        	    	evt.save(evento);
        	    	response.setStatus(HttpServletResponse.SC_OK);
        	    }catch(DataIntegrityViolationException e){
        	    	System.out.println(e);
        	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome ou Razao já existente no Banco de dados.");
        	    }
    		}else{
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
    		}
    		
    	}
    }
    
    @RequestMapping(path = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEvento(@RequestBody Map<String, String> json, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
    	
    	if(json.get("cidade")== null || json.get("data") == null || json.get("emp_id")== null || json.get("id") == null || json.get("latitude") == null || json.get("nome") == null || json.get("longitude") == null || json.get("lotacao") == null ||
        json.get("id").equals("") || json.get("data").equals("") || json.get("cidade").equals("") || json.get("emp_id").equals("") || json.get("latitude").equals("") || json.get("nome").equals("") || json.get("longitude").equals("") || json.get("lotacao").equals("")){
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
    	}else{
    		Empresa empresa = new Empresa();
    		empresa = emp.getById(Integer.valueOf(json.get("emp_id")));
    		if(empresa != null){
    			Evento evento = new Evento();
    			evento.setId(Integer.valueOf(json.get("id")));
        		evento.setLatitude(json.get("latitude"));
        		evento.setLongitude(json.get("longitude"));
        		evento.setLotacao(Integer.valueOf(json.get("lotacao")));
        		evento.setNome(json.get("nome"));
        		evento.setEmpresa(empresa);
        		evento.setCidade(json.get("cidade"));
        		evento.setData(json.get("data"));
        	    try{
        	    	evt.save(evento);
        	    	response.setStatus(HttpServletResponse.SC_OK);
        	    }catch(DataIntegrityViolationException e){
        	    	System.out.println(e);
        	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome ou Razao já existente no Banco de dados.");
        	    }
    		}else{
    			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados Invalidos");
    		}
    	}
    }
    
    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEvento(@PathVariable("id") int evtId, HttpServletResponse response) throws IOException{
    	Evento evento = evt.getById(Integer.valueOf(evtId));
    	if(evento != null){
	    	try{
	    		evt.delete(evento);
	    		response.setStatus(HttpServletResponse.SC_OK);
	    	}catch(DataIntegrityViolationException e){
	    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ocorreu um erro durante o processo.");
	    	}
    	}else{
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Evento não encontrada na Base de Dados.");
    	}
    }
    
    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public Iterable<Evento> getAll(HttpServletResponse response){
    	response.setStatus(HttpServletResponse.SC_OK);
    	return evt.getAll();
    }
    
    @RequestMapping(value = "/get")
    @PreAuthorize("isAuthenticated()")
    public Iterable<Evento> pesquisar(@RequestParam(value="nome", defaultValue="%") String nome,@RequestParam(value="cidade", defaultValue="%") String cidade,
    		@RequestParam(value="data", defaultValue="%") String data, HttpServletResponse response)  {
    	response.setStatus(HttpServletResponse.SC_OK);
    	return evt.getByInfo(nome, cidade, data);
	}
    @RequestMapping(value = "/getByEmpresa")
    @PreAuthorize("isAuthenticated()")
    public Iterable<Evento> pesquisarEmpresa(@RequestParam(value="emp_id", defaultValue="%") String emp_id, HttpServletResponse response)  {
    	response.setStatus(HttpServletResponse.SC_OK);
    	return evt.getByEmpresa(Integer.valueOf(emp_id));
	}
}
