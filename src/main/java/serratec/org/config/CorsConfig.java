package serratec.org.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        
        // Adicione o domínio do Vercel aqui também
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:8081",
            "http://localhost:3000",
            "https://estetica-react-front.vercel.app", // DOMÍNIO DO FRONTEND
            "https://estetica-backend-0gia.onrender.com"
        ));
        
        config.setAllowedHeaders(Arrays.asList(
            "Origin", "Content-Type", "Accept", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"
        ));
        
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"
        ));
        
        config.setExposedHeaders(Arrays.asList(
            "Authorization", "Access-Control-Allow-Origin", 
            "Access-Control-Allow-Credentials"
        ));
        
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}