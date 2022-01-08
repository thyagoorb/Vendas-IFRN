
package br.edu.ifrn.crud.controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;
/**
 * 
 * @author thyago
 *
 */

@Component
public class PerfilComponent {
		
		/**
		 * importação da interfce usuarioRepository
		 */
		//
		@Autowired
		private UsuarioRepository usuarioRepository;
		
		/**
		 * método do tipo Usuario responsável por pegar os dados do usuário logado que estão no banco de dados
		 * @return
		 */
	//
		public Usuario getUsuarioLogado() {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
			String currentPrincipalName = authentication.getName();
			
			Usuario usuario = usuarioRepository.findByEmail(currentPrincipalName).get();
	
			return usuario;
		}
		
}
