package library.booklet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.requiresChannel(channel -> channel.anyRequest().requiresSecure()) // redirect HTTP requests to HTTPS
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .csrf(csrf -> csrf.disable())
//                .formLogin(login -> login.disable())
//                .httpBasic(httpBasic -> httpBasic.disable());
//        return http.build();
//    }
}
