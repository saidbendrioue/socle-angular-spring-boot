package com.bycnit.socle.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Status;
import com.bycnit.socle.constants.WebConstants;


import com.bycnit.socle.dto.UserDto;
import com.bycnit.socle.security.JwtAuthenticationRequest;
import com.bycnit.socle.security.JwtAuthenticationResponse;
import com.bycnit.socle.security.JwtTokenUtil;
import com.bycnit.socle.service.exception.AuthenticationException;
import com.bycnit.socle.util.WebUtils;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping(WebConstants.LOGIN)
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody final JwtAuthenticationRequest authenticationRequest) {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return new JwtAuthenticationResponse(token, userDetails);
    }

    @GetMapping(WebConstants.REFRESH)
    public JwtAuthenticationResponse refreshAndGetAuthenticationToken(final HttpServletRequest request) {
        final String authToken = request.getHeader("Authorization");
        final String token = authToken.substring(7);
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDto user = (UserDto) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            final String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new JwtAuthenticationResponse(refreshedToken, user);
        } else {
            return null;
        }
    }

    @GetMapping(WebConstants.SSO)
    public JwtAuthenticationResponse createAuthenticationToken(final HttpServletRequest request) {

        final String username = WebUtils.getConnectedUser(request);

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return new JwtAuthenticationResponse(token, userDetails);
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(final String username, final String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (final BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", Status.UNAUTHORIZED, e);
        }
    }
}
