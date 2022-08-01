package com.khai.blogapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name = "name")
	private RoleName name;
	
	@ManyToMany
	@JoinTable(
			name = "user_role",
			joinColumns =  @JoinColumn(name = "role_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)
	private List<User> users;

	@JsonIgnore
	public List<User> getUsers() {
		return users == null ? null : new ArrayList<>(this.users);
	}

	public void setUsers(List<User> users) {
		if(users == null) {
			this.users = null;
		}else {
			this.users = users;	
		}
		
	}
	
	
}
