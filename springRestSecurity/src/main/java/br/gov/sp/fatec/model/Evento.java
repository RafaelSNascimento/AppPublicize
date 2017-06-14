package br.gov.sp.fatec.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

	//private static final long serialVersionUID = 1507218635788384719L;

	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "EV_ID")
	private int id;
    
    @Column(name = "EV_NOME", length = 45, nullable = false)
    private String nome;
    
    @Column(name = "EV_DATA", length = 45, nullable = false)
    private String data;
    
    @Column(name= "EV_LAT", length = 45, nullable = false)
    private String latitude;
    
    @Column(name = "EV_LONG", length = 45, nullable = false)
    private String longitude;
    
    @Column(name= "EV_CIDADE" , length = 45, nullable = false)
    private String cidade;
    
    @Column(name = "EV_LOTACAO", length = 11, nullable = false)
    private int lotacao;
    

    
    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Empresa empresa;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public int getLotacao() {
		return lotacao;
	}


	public void setLotacao(int lotacao) {
		this.lotacao = lotacao;
	}
	
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
