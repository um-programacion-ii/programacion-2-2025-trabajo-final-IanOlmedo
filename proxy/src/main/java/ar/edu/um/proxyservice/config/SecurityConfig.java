package ar.edu.um.proxyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Para APIs REST, desactivamos CSRF
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Dejamos públicos los endpoints del proxy
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/proxy/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // Cualquier otra cosa (si existiera) sigue requiriendo auth
                        .anyRequest().authenticated())
                // Si en algún momento querés usar Basic Auth en otros endpoints
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
