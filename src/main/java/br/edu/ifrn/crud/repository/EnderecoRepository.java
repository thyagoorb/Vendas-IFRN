package br.edu.ifrn.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Endereco;
import br.edu.ifrn.crud.dominio.Localizacao;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	
	@Query("select e from Endereco e where e.nome like %:nome% ")
	List<Endereco> findByNome(@Param("nome") String nome);
}
