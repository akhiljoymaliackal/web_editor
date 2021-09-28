package com.akhil.security;

import com.akhil.SpringApplicationContext;
import com.akhil.dto.UserDto;
import com.akhil.entity.request.UserLoginRequest;
import com.akhil.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
       AuthenticationManager authenticationManager;

       public UserAuthenticationFilter(AuthenticationManager authenticationManager){
              this.authenticationManager = authenticationManager;
       }


       @Override
       public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
              log.info("Authentication Filter Called");
              UserLoginRequest userLoginRequest = null;
              try {
                     userLoginRequest = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
              } catch (IOException e) {
                     e.printStackTrace();
              }
              return  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),userLoginRequest.getPassword(),new ArrayList<GrantedAuthority>()));
       }

       @Override
       public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
              log.info("Successful authentication for user - " + ((User)(authResult.getPrincipal())).getUsername());
              String token = Jwts.builder()
                      .setSubject(((User)(authResult.getPrincipal())).getUsername())
                      .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                      .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECURITY_TOKEN)
                      .compact();
              UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
              UserDto userDto = userService.getUserByEmailId(((User)(authResult.getPrincipal())).getUsername());
              response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
              response.addHeader("UserId",userDto.getUserId());
       }


}
