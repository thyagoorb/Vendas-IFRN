package br.edu.ifrn.crud.controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class CadastroUsuarioController {
	
	/**
	 * importação da interfce usuarioRepository
	 */
	//
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
	 * Método de validação de dados que retorna uma lista de mensagens 
	 * 
	 */
	
	//
	private List<String> validarDados(Usuario usuario) {
		List<String> msgs = new ArrayList<>();
		
		/**
		 * Validação e comparação de senhas do cadastro 
		 */
		//
		if (!usuario.getSenha().equals(usuario.getSenha2())) {
			msgs.add("Senha incorreta");
		}
		return msgs;
	}
	
	
	/**
	 * método por direcionar para a página usuario/cadastro e criar novo objeto do tipo Usuario
	 * 
	 */
	//
	@GetMapping("/cadastro")
	public String entrarCadastro(ModelMap model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario/cadastro";
	}
	
	
	/**
	 * método responsável por salvar usuário no banco de dados
	 * 
	 */
	//
	@PostMapping("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, ModelMap model, RedirectAttributes attr,
			HttpSession sessao) {

		/**
		 * lista de mensagens de validação, caso tenha algum erro no cadastro
		 * será redirencionado para página usuario/cadastro
		 */
		//
		List<String> msgValidacao = validarDados(usuario);

		if (!msgValidacao.isEmpty()) {
			model.addAttribute("msgsErro", msgValidacao);
			return "usuario/cadastro";
		}
		if (result.hasErrors()) {
			return "usuario/cadastro";
		}
		
		
		/**
		 * validação de email, para que não existam emails iguais no banco de dados
		 */
		//
		Usuario emailInvalido = usuarioRepository.findByEmaill(usuario.getEmail());
		
		/**
		 * validação de telefone, para que não existam telefones iguais no banco de dados
		 */
		//
		Usuario telefoneInvalido = (Usuario) usuarioRepository.findByTelefone(usuario.getTelefone());
		
		/**
		 * condições de validação de email e telefone
		 */
		//
		if(usuario.getId() == 0) {
			
			if(emailInvalido != null) {
			model.addAttribute("msgErro", "Este email já está cadastrado");
			return "/usuario/cadastro";
		}
			else if(telefoneInvalido != null) {
			model.addAttribute("msgErro", "Este telefone já está cadastrado");
			return "/usuario/cadastro";
		}
		}
		/**
		 * Criptografando senha do usuário
		 */
		//
		String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		/**
		 * Cadastrando usuário no banco de dados
		 */
		//
		usuarioRepository.save(usuario);
		attr.addFlashAttribute("msgSucesso", "Operação realizada com sucesso!");

		return "redirect:/usuarios/cadastro";
	}
	
	
	/**
	 * método para editar usuário cadastrado a partir do seu id
	 * 
	 */
	//
	@GetMapping("/editar/{id}")
	public String iniciarEdicao(@PathVariable("id") Integer idUsuario, ModelMap model, HttpSession sessao) {

		Usuario u = usuarioRepository.findById(idUsuario).get();

		model.addAttribute("usuario", u);

		return "usuario/cadastro";
	}
	
	/**
	 * método que retorna uma lista de Strings com os cursos
	 * @return
	 */
	//
	@ModelAttribute("cursos")
	public List<String> getCursos() {
		return Arrays.asList("Administração", "Eletrônica", "Energias Renováveis", "Física", "Informática");

	}
	
	/**
	 * método responsável por redirecionar para a área de login
	 * 
	 */
	//
	@GetMapping("/login")
	public String login(Usuario usuario) {
		return "usuario/cadastro";
		
	}
	/**
	 * método responsável validar erros na área de login
	 * 
	 */
	//
	@GetMapping("/login-error")
	public String loginError(ModelMap model) {
		model.addAttribute("msgsErro", "Login ou senha incorretos");
		return "redirect:/usuarios/cadastro";
		
	}
	}


