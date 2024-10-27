package emrx.monster.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private static final String[] NO_AUTH_URLS = {
            "v3/api-docs/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/configuration/security",
            "/webjars/**",
            "/login",
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(NO_AUTH_URLS).permitAll()
                        .requestMatchers("/monsters/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/traits/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/traits/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/traits/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/traits/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("user").roles("USER").build());
        manager.createUser(users.username("admin").password("qwerty").roles("ADMIN").build());
        return manager;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(Arrays.asList("Origin, Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        cc.addAllowedOriginPattern("*");
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);

        return source;
    }
}
