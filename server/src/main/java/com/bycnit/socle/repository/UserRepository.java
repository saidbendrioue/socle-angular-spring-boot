package com.bycnit.socle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bycnit.socle.domain.User;

/**
 * DAO for user management
 *
 * @author S.BENDRIOUE
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	Optional<User> findByUsername(String username);
}
