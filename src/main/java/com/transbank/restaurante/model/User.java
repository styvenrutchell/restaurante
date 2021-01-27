package com.transbank.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
	
	@Id
	private int id;
	
	private String userName;
	private String password;
	private boolean active;
	private String roles;

}
