package br.edu.ifrn.crud.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 
 * @author thyago
 * Classe declarada como entidade a ser salva no banco de dados
 *
 */
@Entity
public class Usuario {
	
	/**
	 * Atributos estatisticos de segurança do sistema
	 */
	
	public static final String ADMIN = "ADMIN";
	public static final String USUARIO_COMUM = "COMUM";
	
	
	
	/**
	 * Atributos 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Nome obrigatório.")
	@Size(min = 4, message = "Nome muito curto")
	@Column(nullable = false)
	private String nome;
	
	@NotBlank(message = "Email obrigatório.")
	@Column(nullable = false)
	private String email;
	
	@NotBlank(message = "Senha obrigatória.")
	@Size(min = 6, message = "Senha muito curta.")
	@Column(nullable = false)
	private String senha;
	
	@NotBlank(message = "Repita sua senha.")
	@Size(min = 6, message = "Senha muito curta.")
	@Column(nullable = false)
	private String senha2;
	
	@NotBlank(message = "Telefone é obrigatório.")
	@Size(min = 15, message = "Telefone inválido.")
	@Column(nullable = false)
	private String telefone;
	
	@NotBlank(message = "Curso é obrigatório.")
	@Column(nullable = false)
	private String curso;
	
	@Column(nullable = false)
	private String perfil = USUARIO_COMUM;
	
	/**
	 * hashCode para o Java saber identificar diferentes tipos de objetos do tipo Produto
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * GETS E SETS
	 * @return
	 */
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSenha2() {
		return senha2;
	}
	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	
}
