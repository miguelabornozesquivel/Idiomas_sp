package pe.edu.upc.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import pe.edu.upc.spring.auth.handler.LoginSuccessHandler;
import pe.edu.upc.spring.serviceimpl.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/index/");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		try {
			http.authorizeRequests()
			.antMatchers("/idiomas/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/dias/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/alumnos/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/profesores/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/cursos/irRegistrar").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')")
			.antMatchers("/cursos/").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ALUMNO') or hasRole('ROLE_PROFESOR')")		
			.antMatchers("/sesiones/irRegistrar").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PROFESOR')")
			.antMatchers("/sesiones/").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ALUMNO') or hasRole('ROLE_PROFESOR')")
			.antMatchers("/matriculas/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ALUMNO')")
			.antMatchers("/welcome/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_ALUMNO') or hasRole('ROLE_PROFESOR')").and()
			.formLogin().successHandler(successHandler).loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/welcome/bienvenido")
			.permitAll().and().logout().logoutSuccessUrl("/login").permitAll().and().exceptionHandling().accessDeniedPage("/error_403").and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}