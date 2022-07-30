package com.khai.blogapi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.khai.blogapi.model.Role;
import com.khai.blogapi.model.User;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Boolean enabled;
	private List<GrantedAuthority> authorities;

	

	public UserPrincipal(Long id, String username, String firstName, String lastName, String email, String password,
			Boolean enabled, List<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		
		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
	}

	public static UserPrincipal create(User user) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		System.out.println(user.getRoles().get(0).getName());
		for(Role r : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
		}
		
		return new UserPrincipal(user.getId(),
								user.getUsername(),				
								user.getFirstName(),
								user.getLastName(),
								user.getEmail(),
								user.getPassword(),
								user.isEnabled(),
								authorities);
								
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities == null ? null : new ArrayList<>(this.authorities);
	}

	@Override
	public String getPassword() {
		return passwordEncoder().encode(this.password);
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) object;
		return Objects.equals(id, that.id);
	}

	public int hashCode() {
		return Objects.hash(id);
	}
	
	

	
}
