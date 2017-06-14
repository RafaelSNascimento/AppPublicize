package br.gov.sp.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.model.Empresa;
import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.repository.EmpresaRepository;
import br.gov.sp.fatec.repository.UsuarioRepository;

@Service("EmpresaService")
public class EmpresaServiceImpl implements EmpresaService{
	@Autowired
	private UsuarioRepository userRepo;
	
	public void setUsuarioRepository(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}
	@Autowired
	private EmpresaRepository empresaRepo;
	
	public void setEmpresaRepository(EmpresaRepository empresaRepo) {
		this.empresaRepo = empresaRepo;
	}
	@Override
	public Empresa save(Empresa empresa,String username) {
		Usuario usuario = new Usuario();
		usuario = userRepo.findByNome(username);
		
		empresaRepo.save(empresa);
		
		Empresa emp = new Empresa();
		emp = empresaRepo.findByNome(empresa.getNome());
		usuario.setEmpresa(emp);
		userRepo.save(usuario);
		System.out.println(emp);
		return emp;
	}
	@Override
	public Empresa update(Empresa empresa) {
		return empresaRepo.save(empresa);
	}
	@Override
	public void delete(Empresa empresa) {
		// TODO Auto-generated method stub
		empresaRepo.delete(empresa);
		
	}

	@Override
	public Empresa getById(int id) {
		return empresaRepo.findOne(id);
	}
	
	@Override
	public Empresa getByNome(String nome) {
		return empresaRepo.findByNome(nome);
	}

}
