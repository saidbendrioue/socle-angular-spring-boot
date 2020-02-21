package com.bycnit.socle.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bycnit.socle.domain.User;
import com.bycnit.socle.mapper.IUserMapper;
import com.bycnit.socle.repository.UserRepository;

/**
 * @author S.BENDRIOUE
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private IUserMapper mapper;

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {

        final Optional<User> admin = repository.findByUsername(username);

        if (admin.isPresent()) {
            return mapper.asUserDto(admin.get());
        }
        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    }
}
