package br.edu.ifrn.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Localizacao;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer>{
	
	@Query("select l from Localizacao l where l.nome like %:nome% ")
	List<Localizacao> findByNome(@Param("nome") String nome);

}
