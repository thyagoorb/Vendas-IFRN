
package br.edu.ifrn.crud.controladores;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import br.edu.ifrn.crud.dominio.Produto;
import br.edu.ifrn.crud.repository.ProdutoRepository;
/**
 * 
 * @author thyago
 *
 */
@Controller
public class PublicacoesController {
	
		/**
		 * importação da interfce ProdutoRepository e da classe PerfilComponent
		 */
		//
		@Autowired
		private ProdutoRepository produtoRepository;
		
		@Autowired
		private PerfilComponent perfilComponent;
		
		
		/**
		 * método responsável pela exibição dos produtos cadastrados a partir da lista de Produtos: produtosMostrados
		 * após isso, os dados são enviados para a página: publicacoes/publicacao
		 * 
		 */
		//
		@GetMapping("/publicacao")
		public String publicacao( ModelMap model) {
			
			List<Produto> produtosMostrados = produtoRepository.findAll();
			
			model.addAttribute("produtosMostrados", produtosMostrados);
			
			/**
			 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
			 */
			//
			model.addAttribute("dados", perfilComponent.getUsuarioLogado());
			
			return "publicacoes/publicacao";
		}
		
		
		/**
		 * método responsável pela filtragem de categorias da classe Produto
		 * Após fazer uma pesquisa no banco por categorias, os dados são redirecionados para a página publicacoes/publicacao
		 * 
		 */
		//
		@GetMapping("/publicacao/filtrar")
		public String filtrarPorCategoria( @RequestParam(name="categoria", required=false) String categoria, 
				ModelMap model) {
			
			List<Produto> produtosMostrados = produtoRepository.findByCategoria(categoria);
			model.addAttribute("produtosMostrados", produtosMostrados);
			
			/**
			 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
			 */
			//
			model.addAttribute("dados", perfilComponent.getUsuarioLogado());
			
			
			return "publicacoes/publicacao";
		}
		
		
		
}
