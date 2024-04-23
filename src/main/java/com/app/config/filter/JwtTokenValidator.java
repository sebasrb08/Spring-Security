package com.app.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//este va a ser nuestro filtro que va a validar si nuestro token es valido
// va a ejecutar este filtro por cada request,con esto garantizamos siempre la validacion del token
public class JwtTokenValidator extends OncePerRequestFilter {
    
    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
            //obtiene el header de la peticion
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            
            if (jwtToken != null) {

                //extrae el String desde el index 7
                jwtToken = jwtToken.substring(7);

                //validamos el token
                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

                //extraemos el usario y enviamos el token decodificado
                String username= jwtUtils.extractUsername(decodedJWT);
                //recuperamos los permisos que tiene el usuario
                String StringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                // seteamos el usuario

                // los permisos separados por coma los convierte  en una lista
                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringAuthorities);
                
                //extraemos el contexto de spring security
                SecurityContext context = SecurityContextHolder.getContext();
                //estoy declarando el objeto authentication
                Authentication authentication = new UsernamePasswordAuthenticationToken( username,  null,authorities);

                //enviamos la autenticacion del usuario
                context.setAuthentication(authentication);

                //le seteamos el contexto
                SecurityContextHolder.setContext(context);
            }

            filterChain.doFilter(request, response);

    }
    
}
