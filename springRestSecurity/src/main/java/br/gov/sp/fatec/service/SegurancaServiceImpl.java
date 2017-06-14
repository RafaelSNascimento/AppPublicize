package br.gov.sp.fatec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.sp.fatec.model.Autorizacao;
import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.repository.AutorizacaoRepository;
import br.gov.sp.fatec.repository.UsuarioRepository;

@Service("segurancaService")
public class SegurancaServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private AutorizacaoRepository authRepo;

	/**
	 * @param usuarioRepo the usuarioRepo to set
	 */
	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	
	public AutorizacaoRepository getAuthRepo() {
		return authRepo;
	}


	public void setAuthRepo(AutorizacaoRepository authRepo) {
		this.authRepo = authRepo;
	}


	public UsuarioRepository getUsuarioRepo() {
		return usuarioRepo;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.findByNome(username);
		if(usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return usuario;
	}
	
	public boolean userCreate(String nome, String senha){
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setSenha(senha);
		List<Autorizacao> auth = authRepo.findByNomeContainsIgnoreCase("ROLE_ADMIN");
		usuario.setAutorizacoes(auth);
		usuarioRepo.save(usuario);
		return false;
	}
}
