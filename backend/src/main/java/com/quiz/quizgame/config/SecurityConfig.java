package com.quiz.quizgame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig
{

	@Bean
	public SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception
	{
		http.cors ( cors ->
		            {
		            } ) // active CORS
		    .csrf ( csrf -> csrf.disable () ) // désactive CSRF
		    .authorizeHttpRequests ( auth -> auth.requestMatchers ( "/**" ).permitAll () // toutes les routes ouvertes
		    );
		return http.build ();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource ()
	{
		CorsConfiguration config = new CorsConfiguration ();
		config.setAllowedOrigins ( List.of ( "http://localhost:5173" , "https://quizgame-page.onrender.com" ) );
		config.setAllowedMethods ( List.of ( "GET" , "POST" , "PUT" , "DELETE" , "OPTIONS" ) );
		config.setAllowCredentials ( true );
		config.setAllowedHeaders ( List.of ( "*" ) );

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ();
		source.registerCorsConfiguration ( "/**" , config );
		return source;
	}
}
