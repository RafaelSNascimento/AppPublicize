/**
 * 
 */
package br.gov.sp.fatec;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.gov.sp.fatec.model.Usuario;
import br.gov.sp.fatec.repository.UsuarioRepository;

/**
 * @author Emanuel
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/UsuarioRepositoryTest-context.xml" })
@Transactional
public class UsuarioRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	private static final String NOME = "Usuário X";
	private static final String SENHA = "X";
	private static final String CPF = "12357786";
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	/**
	 * @param usuarioRepo the usuarioRepo to set
	 */
	public void setUsuarioRepo(UsuarioRepository usuarioRepo) {
		this.usuarioRepo = usuarioRepo;
	}

	@Test
	public void testeInsercaoOk() {
		Usuario usuario = new Usuario();
		usuario.setNome(NOME);
		usuario.setSenha(SENHA);
		usuario.setCPF(CPF);
		usuarioRepo.save(usuario);
		assertTrue(usuario.getId() != null);
	}

}
