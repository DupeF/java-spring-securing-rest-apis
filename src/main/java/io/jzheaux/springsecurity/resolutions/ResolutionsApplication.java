package io.jzheaux.springsecurity.resolutions;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ResolutionsApplication.class, args);
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new InMemoryUserDetailsManager(
//				org.springframework.security.core.userdetails.User
//					.withUsername("user")
//					.password("{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W")
//					.authorities("resolution:read")
//					.build());
//	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		return new UserRepositoryUserDetailsService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(auth -> auth
				.mvcMatchers(HttpMethod.GET, "/resolutions", "/resolution/**").hasAuthority("resolution:read")
				.anyRequest().hasAuthority("resolution:write"))
			.httpBasic(basic -> {});
	}
	
	

}
