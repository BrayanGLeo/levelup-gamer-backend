// Archivo: src/main/java/levelup_gamer_backend/config/SecurityConfig.java

package levelup_gamer_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos por conveniencia en REST
            .cors(Customizer.withDefaults()) // Habilitamos CORS
            .authorizeHttpRequests(auth -> auth
                
                // Rutas públicas (Tienda y Autenticación)
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/checkout/**").permitAll() 
                .requestMatchers("/api/v1/tienda/**").permitAll() 
                
                // --- RESTRICCIONES POR ROL (PDF) ---
                
                // Rutas exclusivas para Administrador
                .requestMatchers("/api/v1/admin/usuarios/**").hasAuthority("Administrador")
                .requestMatchers("/api/v1/admin/categorias/**").hasAuthority("Administrador")
                
                // Rutas compartidas (Admin y Vendedor)
                .requestMatchers("/api/v1/productos/**").hasAnyAuthority("Administrador", "Vendedor")
                .requestMatchers("/api/v1/ordenes/**").hasAnyAuthority("Administrador", "Vendedor")
                .requestMatchers("/api/v1/admin/reportes/**").hasAnyAuthority("Administrador", "Vendedor")
                
                // Cualquier otra ruta no especificada debe estar autenticada
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
            .formLogin(Customizer.withDefaults()); // Usamos el manejo de sesión por defecto de Spring

        return http.build();
    }

    // Bean para codificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}