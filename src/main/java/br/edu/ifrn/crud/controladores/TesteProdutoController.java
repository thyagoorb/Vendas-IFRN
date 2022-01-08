package br.edu.ifrn.crud.controladores;
import br.edu.ifrn.crud.dominio.Arquivo;
import br.edu.ifrn.crud.dominio.Endereco;
import br.edu.ifrn.crud.dominio.Localizacao;
import br.edu.ifrn.crud.dominio.Produto;
import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.dto.AutocompleteDTO;
import br.edu.ifrn.crud.repository.ArquivoRepository;
import br.edu.ifrn.crud.repository.EnderecoRepository;
import br.edu.ifrn.crud.repository.LocalizacaoRepository;
import br.edu.ifrn.crud.repository.ProdutoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * 
 * @author josiv
 * Essa classe controladora é responsável por fazer a publicação das ofertas,
 * recebendo e enviando dados de anunciante para comprador fazendo conexões
 * com o banco de dados através dos repositórios. Também, podemos encontrar
 * o UPDATE da sigla CRUD (create, read, update & delete). 
 *
 */
@Controller
@RequestMapping("/vendas")
public class TesteProdutoController {
	
	/**
	 * Declaração das váriaveis como repositórios responsáveis por 
	 * fazer uma conexão com o banco de dados. São Interfaces Java.
	 */
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ArquivoRepository arquivoRepository;
	
	@Autowired
	private LocalizacaoRepository localizacaoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PerfilComponent perfilComponent;
	
	/**
	 * O método abaixo é responsável por mapear e identificar uma classe java
	 * para entrar em contato com uma página HTML. Nesse caso, o mapeamento a
	 * seguir seguiria um modelo e a URL do navegador mostraria site/vendas/
	 * anuncios.
	 * 
	 * 
	 * @param model
	 * 
	 * foi adicionado um parametro model do tipo ModelMap para enviar informações
	 * da classe controladora para a página HTML.
	 * 
	 * @return
	 * 
	 * No final o método retorna todas as modificações feitas dentro
	 * da função para a página HTML para que sejam realizadas.
	 */
	@GetMapping("/anuncios")
	public String entrar(ModelMap model) {
		/**
		 * Utilizando uma propriedade do ModelMap, o método abaixo é reposável
		 * por instanciar um novo objeto da classe Produto e envia-lo para a
		 * página HTML a fim de que seus atributos sejam preenchidos por informações
		 * passadas pelo cliente no formulário da página HTML.
		 */
		model.addAttribute("produto", new Produto());
		
		/**
		 * @author thyago
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		/**
		 * No final o método retorna todas as modificações feitas dentro
		 * da função para a página HTML para que sejam realizadas.
		 */
		return "venda/anuncio";
	}
	/**
	 * Seguindo o mapeamento, o método abaixo vai resgatar todas as informações
	 * fornecidas pelo cliente no formulário para trata-las.
	 * 
	 * @param produto
	 * 
	 * o parametro produto serve para preencher informações utilizando
	 * conceitos de PEOO.
	 * 
	 * @param model
	 * 
	 * o parametro model para enviar informações
	 * da classe controladora para a página HTML.
	 * 
	 * @param arquivo
	 * 
	 * um parametro arquivo para receber e tratar um objeto file, imagens por exemplo.
	 * 
	 * @param attr
	 * 
	 * um parametro
	 *  attr do tipo RedirectAttributes para enviar um mensagens de flash para
	 *  página HTML, mensagens de rápido aparecimento.
	 *   
	 * @param sessao
	 * 
	 * um parametro sessao do tipo HttpSession para tratar informações na mémoria.
	 * 
	 * @return
	 */
	@PostMapping("/publicar")
	@Transactional(readOnly = false)
	public String publicar(Produto produto, ModelMap model, @RequestParam("file") MultipartFile arquivo, 
			RedirectAttributes attr, HttpSession sessao) {
		
		/**
		 * @author thyago
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		
		/**
		 * A lista e condição "if" abaixo é responsável por armazenar Strings de mensagens
		 * de erro, caso haja campos em branco, para validar os dados. Para enviar informações
		 * À página foi usado um model do ModelMap.
		 */
		List<String> msgValidacao = validarDados(produto);
		
		if(!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro", msgValidacao);
			return "venda/anuncio";
		}
		
		/**
		 * A série de métodos inclusa no "try/catch" abaixo é responsável
		 * por validar o recebimento de um arquivo do tipo imagem para que
		 * possa ser adicionada junto as informações de um produto ao banco
		 * de dados.
		 */
		try {
			
			if(arquivo != null && !arquivo.isEmpty()) {
				
				String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
				
				Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());
				
				arquivoRepository.save(arquivoBD);
				
				if(produto.getFoto() != null && produto.getFoto().getId() != null && produto.getFoto().getId() > 0) {
					arquivoRepository.delete(produto.getFoto());
				}
				
				produto.setFoto(arquivoBD);
				
			}else {
				produto.setFoto(null);
			}
			
			produto.setVendedor(perfilComponent.getUsuarioLogado().getNome());
			
			
			produtoRepository.save(produto);
			attr.addFlashAttribute("msgSucesso", "Operação realizada!");
			attr.addFlashAttribute("dados", perfilComponent.getUsuarioLogado());
			
			/**
			 * @author thyago
			 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
			 */
			model.addAttribute("dados", perfilComponent.getUsuarioLogado());
			
		}catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/vendas/anuncios";
	}
	/**
	 * o Mapeamento a seguir foi desenvolvido para o UPDATE da sigla 
	 * CRUD (create, read, update & delete), para fazer modificações 
	 * de dados de um produto no banco de dados, caso o cliente queira.
	 * 
	 * @param nomeProduto
	 * 
	 * parametro responsável por capturar o produto a fim de que seja usado
	 * na modificação
	 * 
	 * @param model
	 * 
	 * o parametro model para enviar informações
	 * da classe controladora para a página HTML.
	 * 
	 * @param sessao
	 * 
	 * um parametro sessao do tipo HttpSession para tratar informações na mémoria.
	 * 
	 * @return
	 */
	@GetMapping("/editar/{nome}")
	@Transactional(readOnly = true) 
	public String editarProduto(
				@PathVariable("nome") String nomeProduto,
				ModelMap model,
				HttpSession sessao
			) {
				
		Produto p = (Produto) produtoRepository.findByNome(nomeProduto).get(0);
		
		/**
		 * @author thyago
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		model.addAttribute("produto", p);
		
		return "venda/anuncio";
	}
	
	/**
	 * O mapeamento do método abaixo serve para utilizar uma funcionalidade da
	 * biblioteca Jquery, o autocomplete. Para que facilite a digitação implementando
	 * aos campos palavas já salvas no banco de dados.
	 * 
	 * @param termo
	 * o parametro termo serve de comparador na busca da palavra "match" no banco de dados.
	 * @return
	 */
	
	@GetMapping("/autocompleteLocalizacoes")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> AutocompleteLocalizacoes(@RequestParam("term") String termo){
		
		List<Localizacao> localizacoes = localizacaoRepository.findByNome(termo);
		
		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		localizacoes.forEach(l -> resultados.add( new AutocompleteDTO(l.getNome(), l.getId())));
		
		return resultados;
	}
	
	/**
	 * O mapeamento do método abaixo serve para utilizar uma funcionalidade da
	 * biblioteca Jquery, o autocomplete. Para que facilite a digitação implementando
	 * aos campos palavas já salvas no banco de dados.
	 * 
	 * @param termo
	 * o parametro termo serve de comparador na busca da palavra "match" no banco de dados.
	 * @return
	 */
	
	@GetMapping("/autocompleteEnderecos")
	@Transactional(readOnly = true)
	@ResponseBody
	public List<AutocompleteDTO> AutocompleteEnderecos(@RequestParam("term") String termo){
		
		List<Endereco> enderecos = enderecoRepository.findByNome(termo);
		
		List<AutocompleteDTO> resultados = new ArrayList<>();
		
		enderecos.forEach(e -> resultados.add( new AutocompleteDTO(e.getNome(), e.getId())));
		
		return resultados;
	}
	
	/**
	 * O mapeamento do método abaixo serve para adicionar multiplos endereços
	 * de informações ao banco de dados
	 * @param produto
	 * @param model
	 * @return
	 */
	
	@PostMapping("/addEndereco")
	public String addEndereco(Produto produto, ModelMap model) {
		if(produto.getEnderecos() == null) {
			produto.setEnderecos(new ArrayList<>());
		}
		
		produto.getEnderecos().add(produto.getEndereco());
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		return "venda/anuncio";
	}
	
	/**
	 * A lista de Strings abaixo recebe Strings de campos que passaram por um erro
	 * de validação a fim de serem usadas como ferramenta de indicar ao cliente.
	 * 
	 * @param produto
	 * objeto Produto a ser validado.
	 * 
	 * @return
	 */
	private List<String> validarDados(Produto produto){
		List<String> msgs = new ArrayList<>();
		
		if(produto.getNome() == null || produto.getNome().isEmpty()) {
			msgs.add("O campo nome é obrigatório.");
		}
		if(produto.getCategoria().equals("")) {
			msgs.add("O campo categoria é obrigatório.");
		}
		if(produto.getOperacao() == null || produto.getOperacao().isEmpty() || produto.getOperacao().equals("")) {
			msgs.add("O campo operação é obrigatório.");
		}
		if(produto.getOperacao() == null || produto.getOperacao().equals("doar")) {
			produto.setPreco("0 | Doação");
		}
		if(produto.getDescricao() == null || produto.getDescricao().isEmpty()) {
			msgs.add("O campo descrição é obrigatório.");
		}
		if(produto.getContato() == null || produto.getContato().isEmpty()) {
			msgs.add("O campo contato é obrigatório.");
		}
		if(produto.getLocalizacao() == null || produto.getLocalizacao().getNome().isEmpty()) {
			msgs.add("O campo Localização é obrigatório.");
		}
		if(produto.getEndereco() == null || produto.getEndereco().getNome().isEmpty()) {
			msgs.add("O campo Endereço é obrigatório.");
		}
		return msgs;
	}
	
	/**
	 * Método responsável por enviar a página HTML informações no formato lista para
	 * serem utilizadas numa propriedade "select" do HTML.
	 * @return
	 */
	@ModelAttribute("categorias")
	public List<String> getCategorias(){
		return Arrays.asList("HARDWARES", "LIVROS/CADERNOS", "MATERIAL ESCOLAR", "MOCHILAS",
				"NOTEBOOK/DESKTOP", "SOFTWARES");
	}
	
}
