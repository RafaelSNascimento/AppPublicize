package br.gov.sp.fatec.repository;

import org.springframework.data.repository.CrudRepository;

import br.gov.sp.fatec.model.Empresa;


public interface EmpresaRepository extends CrudRepository<Empresa, Integer>{

	public Empresa findByNome(String nome);

}
