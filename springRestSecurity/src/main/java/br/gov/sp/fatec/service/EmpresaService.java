package br.gov.sp.fatec.service;

import br.gov.sp.fatec.model.Empresa;

public interface EmpresaService {
	public Empresa save(Empresa empresa, String username);
	public void delete(Empresa empresa);
	public Empresa getById(int id);
	public Empresa getByNome(String nome);
	public Empresa update(Empresa empresa);
}
