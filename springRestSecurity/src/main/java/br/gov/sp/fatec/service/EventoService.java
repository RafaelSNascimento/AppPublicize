package br.gov.sp.fatec.service;


import java.util.List;

import br.gov.sp.fatec.model.Evento;

public interface EventoService {
	public void save(Evento evento);
	public void delete(Evento evento);
	public Evento getById(int id);
	public Iterable<Evento> getAll();
	public List<Evento> getByInfo(String nome, String cidade, String data);
	public List<Evento> getByEmpresa(int emp_id);
}
