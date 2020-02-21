package com.bycnit.socle.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {
        log.debug("processing authentication for '{}'", request.getRequestURL());

        final String requestHeader = request.getHeader("Authorization");

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            final String authToken = requestHeader.substring(7);
            final String username = getUserName(authToken);

            log.debug("checking authentication for user '{}'", username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("security context was null, so authorizating user");

                // It is not compelling necessary to load the use details from the database. You could also store the information
                // in the token and read it from it. It's up to you ;)
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                // the database compellingly.
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authorizated user '{}', setting security context", username);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } else {
            log.warn("couldn't find bearer string, will ignore the header");
        }

        chain.doFilter(request, response);
    }

    /**
     * Gets the username from the authentication token
     *
     * @param authToken
     *            authentication token
     * @return the username
     */
    private String getUserName(final String authToken) {
        try {
            return jwtTokenUtil.getUsernameFromToken(authToken);
        } catch (final IllegalArgumentException e) {
            log.error("an error occured during getting username from token", e);
        } catch (final ExpiredJwtException e) {
            log.warn("the token is expired and not valid anymore", e);
        }

        return null;
    }
}
