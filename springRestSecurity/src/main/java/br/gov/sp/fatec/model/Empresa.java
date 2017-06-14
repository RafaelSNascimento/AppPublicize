package br.gov.sp.fatec.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa{

	//private static final long serialVersionUID = -7578937961979778761L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "EMP_ID")
	private int id;
    
    @Column(name = "EMP_NOME", unique=true, length = 45, nullable = false)
    private String nome;
    
    @Column(name = "EMP_RAZAOSOCIAL", unique=true, length = 100, nullable = false)
    private String razaoSocial;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}
