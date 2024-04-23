package com.app.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.app.config.filter.JwtTokenValidator;
import com.app.services.UserDetailServiceImpl;
import com.app.util.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;
    
    /*Filtro para verificar la autenticacion */
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
         return httpSecurity
        .csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(http -> {
             /*Configura los endpoints publicos */
             http.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
            
             /*Configura los endpoints privados */
             http.requestMatchers(HttpMethod.POST,"/method/post").hasAnyRole("ADMIN","DEVELOPER");
             http.requestMatchers(HttpMethod.PATCH,"/method/patch").hasAnyAuthority("REFACTOR");
             http.requestMatchers(HttpMethod.GET,"/method/get").hasAnyAuthority("READ");
            //Configura el resto del endpoint NO ESPECIFICADO
             http.anyRequest().denyAll();

        })
        //antes de la clase basicAuthenticationFilter se va a ejecutar el filtro de la valdiacion del token
        .addFilterBefore(new JwtTokenValidator(jwtUtils),BasicAuthenticationFilter.class)
        .build();
     }
    
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
    //     return httpSecurity
    //     .csrf(csrf -> csrf.disable())
    //     .httpBasic(Customizer.withDefaults())
    //     .sessionManagement(sesion -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        
    //     .build();
    // }
    
    /*Administrador de autenticacion */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailServiceImpl){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailServiceImpl);
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
