package br.com.alpi.estoque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class ApplicationConfigSecurity {
	
	 @Bean
	public StrictHttpFirewall httpFirewall() {
		 StrictHttpFirewall firewall = new StrictHttpFirewall();
		    firewall.setAllowSemicolon(true);
		    return firewall;
		}

}
