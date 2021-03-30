package com.app.sec;





import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.Model;

@Configuration
@EnableWebSecurity
public class SecuritéConfig extends WebSecurityConfigurerAdapter{
    
	 @Autowired
	 private DataSource dataSource;
	 public static Model modelclient;
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin").password("admin1234").roles("ADMIN","USER");
//		auth.inMemoryAuthentication().withUser("user").password("user5678").roles("USER");	
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT MAIL, PASSWORD, 'TRUE' FROM user WHERE MAIL=?")
		.authoritiesByUsernameQuery("SELECT MAIL, ROLE, 'TRUE' FROM user_role WHERE MAIL=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(new Md5PasswordEncoder()); 

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
       
		http.formLogin().loginPage("/home");
		http.authorizeRequests().antMatchers("/compte/listeappoffres","/compte/profil","/compte/listeappoffres/attribution","/compte/listeappoffres/annulation").hasRole("USER");
		http.authorizeRequests().antMatchers("/compte/listeClient").hasRole("ADMIN");
		
	} 
}
