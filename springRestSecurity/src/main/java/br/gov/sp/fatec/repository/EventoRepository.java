package br.gov.sp.fatec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.model.Evento;



public interface EventoRepository extends CrudRepository<Evento, Integer>{
	
	@Query("select u from Evento u where u.nome like %?1% and u.cidade like %?2% and u.data like %?3%")
	public List<Evento> getByInfo(String nome, String cidade, String data);
	
	public List<Evento> findByEmpresaId(int id);
	
}
