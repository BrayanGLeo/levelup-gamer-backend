package levelup_gamer_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(AuthenticationProvider authenticationProvider,
            CorsConfigurationSource corsConfigurationSource) {
        this.authenticationProvider = authenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rutas Públicas (SIN AUTENTICACIÓN)
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/tienda/**").permitAll()
                        .requestMatchers("/api/v1/blog/**").permitAll()
                        .requestMatchers("/api/v1/checkout/**").permitAll()

                        // Rutas Privadas (Requieren Login)
                        .requestMatchers("/api/v1/ordenes/mis-pedidos").authenticated()
                        .requestMatchers("/api/v1/usuarios/direcciones/**").authenticated()
                        .requestMatchers("/api/v1/auth/perfil").authenticated()

                        // Rutas de Admin
                        .requestMatchers("/api/v1/productos/**").hasAnyAuthority("Administrador", "Vendedor")
                        .requestMatchers("/api/v1/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/api/v1/ordenes").hasAnyAuthority("Administrador", "Vendedor")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        return http.build();
    }
}