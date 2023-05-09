package com.stock.jwt;

import com.stock.security.UserDetailsServiceImpl;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);

                if (jwtUtil.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.addHeader("Access-Control-Allow-Methods", "*");
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "*");
        } catch (ExpiredJwtException ex) {
            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
            String requestURL = httpServletRequest.getRequestURL().toString();
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshWebToken")) {
                allowForRefreshToken(ex, httpServletRequest);
            } else
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (BadCredentialsException ex) {
            httpServletRequest.setAttribute("exception", ex);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (!"OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());
    }
}
