package br.edu.ifrn.crud.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Produto;
import br.edu.ifrn.crud.repository.ProdutoRepository;
/**
 * 
 * @author josiv
 * Essa classe controladora serve para executar as propriedades de Read e Delete
 * do CRUD (create, read, update & delete). Para manipular informações do banco de
 * dados na página HTML.
 *
 */
@Controller
@RequestMapping("/vendas")
public class BuscaProdutoController {
	
	/**
	 * repositórios, que são Interfaces Java, instanciadas para
	 * as conexões com o banco de dados.
	 */
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PerfilComponent perfilComponent;
	
	/**
	 * O mapeamento do metodo abaixo serve para utilizar a propriedade Read CRUD (create, read, update & delete).
	 * 
	 * @param nome
	 * 
	 * param nome para identificar o objeto correto
	 * 
	 * @param sessao
	 * 
	 * param sessao para tratar informações na memoria
	 * 
	 * @param model
	 * 
	 * param model do ModelMap para enviar informações para a página HTML
	 * @return
	 */
	@GetMapping("/buscar")
	public String buscarProduto(@RequestParam(name = "nome", required=false) String nome, HttpSession sessao, ModelMap model) {

		
		List<Produto> produtosMostrados = produtoRepository.findByNome(nome);
		
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		model.addAttribute("produtosMostrados", produtosMostrados);

		return "venda/meusAnuncios";
	}
	
	/**
	 * O mapeamento do metodo abaixo serve para utilizar a propriedade Delete CRUD (create, read, update & delete).
	 * @param nomeProduto
	 * 
	 * param nomeProduto para identificar o objeto correto
	 * 
	 * @param model
	 * 
	 * param model do ModelMap para enviar informações para a página HTML
	 * 
	 * @param sessao
	 * 
	 * param sessao para tratar informações na memoria
	 * 
	 * @param attr
	 * @return
	 */
	
	@GetMapping("/remover/{nome}")
	@Transactional(readOnly = false)
	public String removerItem(@PathVariable("nome") String nomeProduto, 
			  ModelMap model ,HttpSession sessao, RedirectAttributes attr) {

		
		produtoRepository.deleteByNome(nomeProduto);
		
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
	
		attr.addFlashAttribute("msgSucesso", "Produto removido!");
		
		return "redirect:/vendas/buscar";
	}
}
