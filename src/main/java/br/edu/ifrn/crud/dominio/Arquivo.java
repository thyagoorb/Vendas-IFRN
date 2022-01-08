package br.edu.ifrn.crud.dominio;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
/**
 * 
 * @author thyago
 * Classe declarada como entidade a ser salva no banco de dados
 *
 */
@Entity
public class Arquivo {
	
	/**
	 * Atributos de Arquivo
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nomeArquivo;
	
	private String tipoArquivo;
	
	/**
	 * Array de bytes com notatação @Lob que indica um arquivo grande
		Anotação @Basic(fetch = FetchType.LAZY) que indica um carregamento apenas quando necessário
	 */
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] dados;

	/**
	 * 
	 * construtor
	 */
	
	public Arquivo(Long id, String nomeArquivo, String tipoArquivo, byte[] dados) {
		super();
		this.id = id;
		this.nomeArquivo = nomeArquivo;
		this.tipoArquivo = tipoArquivo;
		this.dados = dados;
	}
	
	
	/**
	 * construtor
	 */
	public Arquivo() {
	}

	/**
	 * GETTERS E SETTERS
	 * @return
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}

}
