package com.akhil.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    public UserAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            String token = request.getHeader(SecurityConstants.HEADER_STRING);
            if (token==null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)){
                chain.doFilter(request,response);
                return;
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            chain.doFilter(request,response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token!=null){
            token = token.replace(SecurityConstants.TOKEN_PREFIX,"");
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECURITY_TOKEN)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user!=null){
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<GrantedAuthority>());
            }
        }
        return null;
    }

}

