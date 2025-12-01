package levelup_gamer_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
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
                        // RUTAS PÚBLICAS (Frontend Cliente)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll() // Login y Registro
                        .requestMatchers("/api/v1/tienda/**").permitAll() // Catálogo y Detalle
                        .requestMatchers("/api/v1/blog/**").permitAll() // Blog
                        .requestMatchers("/api/v1/checkout/**").permitAll() // Pago (invitados)

                        // RUTAS PRIVADAS (Cliente Logueado)
                        .requestMatchers("/api/v1/ordenes/mis-pedidos").authenticated()
                        .requestMatchers("/api/v1/usuarios/direcciones/**").authenticated()
                        .requestMatchers("/api/v1/auth/perfil").authenticated()

                        // RUTAS ADMIN (Panel de Control)
                        .requestMatchers("/api/v1/productos/**").hasAnyAuthority("Administrador", "Vendedor")
                        .requestMatchers("/api/v1/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/api/v1/ordenes").hasAnyAuthority("Administrador", "Vendedor")

                        // CUALQUIER OTRA RUTA
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}