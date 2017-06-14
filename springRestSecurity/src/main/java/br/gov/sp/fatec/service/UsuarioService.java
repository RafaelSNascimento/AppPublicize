package br.gov.sp.fatec.service;

import br.gov.sp.fatec.model.Usuario;

public interface UsuarioService {
	public void save(Usuario usuario);
	public void delete(Usuario usuario);
	public Usuario getById(long id);

}
