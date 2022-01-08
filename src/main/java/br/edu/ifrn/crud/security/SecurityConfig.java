package br.edu.ifrn.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.crud.dominio.Usuario;
import br.edu.ifrn.crud.service.UsuarioService;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioService service;
	
	
	//método de liberação e bloqueio de URLs
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//URLs liberadas
		http.authorizeRequests()
		.antMatchers("/CSS/**").permitAll()
		.antMatchers("/imagens/**").permitAll()
		.antMatchers("/JavaScript/**").permitAll()
		.antMatchers("/usuarios/cadastro").permitAll()
		.antMatchers("/usuarios/salvar").permitAll()
		.antMatchers("/").permitAll()
		
		//URLs para usuários administradores
		.antMatchers("/usuarios/administradorBD", "/usuarios/editar/**", 
				"/usuarios/remover/**").hasAuthority(Usuario.ADMIN)
		
		//URLs de acesso para autenticados
		.anyRequest().authenticated()
		.and()
		
				//página de login do sistema
				.formLogin()
				.loginPage("/usuarios/login")
				//caso o login seja efetuado será redirecionado para /publicacao
				.defaultSuccessUrl("/publicacao", true)
				//caso o login não seja efetuado será redirecionado para /usuarios/login-error
				.failureUrl("/usuarios/login-error")
				.permitAll()
		.and()
				//opção de logout do sistema
				.logout()
				.logoutSuccessUrl("/publicacao")
		.and()
				//lembrar dados do usuário
				.rememberMe();
	}
	//método que recebe senha criptografada da página
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
