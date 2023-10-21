package com.donatoordep.debver.security.filters;

import com.donatoordep.debver.repositories.UserRepository;
import com.donatoordep.debver.security.TokenJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenJWTService service;

    @Autowired
    private UserRepository repository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (recoverToken(request) != null) {
            String login = service.validateToken(recoverToken(request));// Obtém o email do usuário logado
            UserDetails user = repository.findByEmailForUserDetails(login); // Buscando o usuario que fez a requisição

            SecurityContextHolder.getContext().setAuthentication(authentication(user, user.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }

    // Recuperar o token JWT do cabeçalho da requisição
    private String recoverToken(HttpServletRequest request) {
        return (request.getHeader("Authorization") != null)
                ? request.getHeader("Authorization").split(" ")[1] : null;
    }

    // Cria o objeto que irá representar Authentication
    private UsernamePasswordAuthenticationToken authentication(
            UserDetails typeUser, Collection<? extends GrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(typeUser, null, authorities);
    }
}