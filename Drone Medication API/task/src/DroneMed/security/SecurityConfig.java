package DroneMed.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/drones/**").hasRole("ADMIN")
                        .antMatchers("/api/users/get_user/*").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/api/users/**").hasRole("ADMIN")
                        .antMatchers("/api/dispatch/get_dispatch/*").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/api/dispatch/**").hasRole("ADMIN")
                        .antMatchers("/api/medications/get_by_name/*","/api/medications/get_all_medications","/api/medications/get_medication/*" ).permitAll()
                        .antMatchers("/api/medications/**").hasRole("ADMIN")
                        .antMatchers("/api/register_user").permitAll()
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrfConfigurer -> csrfConfigurer.disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
