package br.edu.ifrn.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.crud.dominio.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	@Query("select p from Produto p where p.nome like %:nome%")
	List<Produto> findByNome(@Param("nome") String nome);
	
	@Modifying
	@Query("delete from Produto p where p.nome like %:nome%")
	void deleteByNome(@Param("nome") String nome);
	
	@Query("select p from Produto p where p.categoria like %:categoria%")
	List<Produto> findByCategoria(@Param("categoria") String categoria);
	

}
