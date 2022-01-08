package br.edu.ifrn.crud.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;
/**
 * 
 * @author thyago
 *
 */
@Controller
@RequestMapping("/usuarios")
public class BuscaUsuarioController {
	
	//
	/**
	 * importação da interfce e da classe PerfilComponent
	 */
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilComponent perfilComponent;
	
	
	/**
	 * 
	 * Esse método é responsável por abrir a página HTML: admin/administradorBD
	 */
	//
	@GetMapping("/administradorBD")
	public String entrarBusca(ModelMap model) {
		
		/**
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		//
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		return "admin/administradorBD";
	}
	
	
	/**
	 * 
	 * Esse método é responsável pela busca dos usuários cadastrados, que podem ser pesquisados pelo nome ou email
	 * Ele recebe um ArrayList com os usuários encontrados apartir de uma pesquisa no banco na interface UsuarioRepository
	 * Após isso, a lista usuariosEncontrados é mandada para a página com o ModelMap model
	 */
	//
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name="nome", required=false) String nome,
						@RequestParam(name="email", required=false) String email, 
						HttpSession sessao, ModelMap model) {
		
		List<Usuario> usuariosEncontrados = usuarioRepository.findByEmailAndNome(email, nome);
		
		model.addAttribute("usuariosEncontrados", usuariosEncontrados);
		
		/**
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		//
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		return "admin/administradorBD";
	}
	
	
	/**
	 * Esse método é responsável pela remoção dos usuários cadastrados apartir de seu id
	 * Após a remoção, será exibido a mensagem de sucesso para o usuário
	 * 
	 */
	//
	@GetMapping("/remover/{id}")
	public String remover(@PathVariable("id") Integer idUsuario, HttpSession sessao, 
			ModelMap model ,RedirectAttributes attr) {
		
		usuarioRepository.deleteById(idUsuario);
		attr.addFlashAttribute("msgSucesso", "Usuário removido com sucesso!");
		
		/**
		 * exibição os dados do método getUsuarioLogado() da classe PerfilComponent
		 */
		//
		model.addAttribute("dados", perfilComponent.getUsuarioLogado());
		
		return "admin/administradorBD";
	}
	
}