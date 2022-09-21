package com.khai.blogapi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.khai.blogapi.model.Role;
import com.khai.blogapi.model.User;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;
	private String image;
	private Boolean enabled;

	private List<GrantedAuthority> authorities;

	public UserPrincipal(Long id, String username, String firstName, String lastName, String email, String password,
			String phoneNumber, String image, Boolean enabled, List<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.image = image;
		this.enabled = enabled;
		
		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
		
		
	}

	public static UserPrincipal create(User user) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role r : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
		}
		
		return new UserPrincipal(user.getId(),
								user.getUsername(),				
								user.getFirstName(),
								user.getLastName(),
								user.getEmail(),
								user.getPassword(),
								user.getPhoneNumber(),
								user.getImage(),
								user.isEnabled(),
								authorities);
								
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities == null ? null : new ArrayList<>(this.authorities);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
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
	

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) object;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	
	

	
}
