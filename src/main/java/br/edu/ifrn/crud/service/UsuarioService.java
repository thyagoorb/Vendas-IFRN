package br.edu.ifrn.crud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	//Interface UsuarioRepository
	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//busca pelo username de usuário no banco de dados
		Usuario usuario = repository.findByEmail(username).orElseThrow(() -> 
		//validação caso o usuário não seja encontrado 
		       new UsernameNotFoundException("Usuário não encontrado"));
		
		
		return new User(
				usuario.getEmail(),
				usuario.getSenha(),
				AuthorityUtils.createAuthorityList(usuario.getPerfil())
				);
	}

}
