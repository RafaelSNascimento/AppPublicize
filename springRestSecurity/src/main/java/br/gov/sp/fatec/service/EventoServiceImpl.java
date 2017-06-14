package br.gov.sp.fatec.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.model.Evento;
import br.gov.sp.fatec.repository.EventoRepository;

@Service("EventoService")
public class EventoServiceImpl implements EventoService{
	@Autowired
	private EventoRepository eventoRepo;
	
	public void setEventoRepository(EventoRepository eventoRepo) {
		this.eventoRepo = eventoRepo;
	}
	@Override
	public void save(Evento evento) {
		eventoRepo.save(evento);
	}

	@Override
	public void delete(Evento evento) {
		eventoRepo.delete(evento);
	}

	@Override
	public Evento getById(int id) {
		return eventoRepo.findOne(id);
	}

	@Override
	public Iterable<Evento> getAll() {
		return eventoRepo.findAll();
	}
	@Override
	public List<Evento> getByInfo(String nome, String cidade, String data) {
		// TODO Auto-generated method stub
		return eventoRepo.getByInfo(nome, cidade, data);
	}
	@Override
	public List<Evento> getByEmpresa(int emp_id) {
		// TODO Auto-generated method stub
		return eventoRepo.findByEmpresaId(emp_id);
	}

}
