package com.htsevv.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.htsevv.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(Constant.AUTHORIZATION);
        String jwtToken;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(Constant.BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                AuthenticationPrinciple principal = jwtTokenUtil.parseToken(jwtToken);
                PreAuthenticatedAuthenticationToken authentication =
                        new PreAuthenticatedAuthenticationToken(principal, null,
                                Arrays.asList(new SimpleGrantedAuthority(principal.getAuthority())));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JWTVerificationException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }

}
