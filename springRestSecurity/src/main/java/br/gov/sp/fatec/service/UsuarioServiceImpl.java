package br.gov.sp.fatec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.repository.UsuarioRepository;

@Service("UsuarioService")
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository userRepo;
	
	@Override
	public void save(Usuario usuario) {
		//List<Autorizacao> auth = authRepo.findByNomeContainsIgnoreCase("ROLE_ADMIN");
		//usuario.setAutorizacoes(auth);
		//usuarioRepo.save(usuario);
		userRepo.save(usuario);
		
	}

	@Override
	public void delete(Usuario usuario) {
		userRepo.delete(usuario);
	}

	@Override
	public Usuario getById(long id) {
		return userRepo.findOne(id);
	}
	

}
