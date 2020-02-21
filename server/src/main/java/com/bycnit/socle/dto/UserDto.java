package com.bycnit.socle.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * The User data transfer object
 *
 * @author S.BENDRIOUE
 */
@Data
public class UserDto implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String imgUrl;

	private String role;
	
	private boolean active;
	
	@JsonIgnore
	private List<GrantedAuthority> authorities;

	@JsonIgnore
	private String password;

	private String username;

	@JsonIgnore
	private boolean accountNonExpired;

	@JsonIgnore
	private boolean accountNonLocked;

	@JsonIgnore
	private boolean credentialsNonExpired;

	@JsonIgnore
	private boolean enabled;

	@JsonIgnore
	private Date lastPasswordResetDate;
	
	@Override
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(this.role));
		return roles;
	}
}
