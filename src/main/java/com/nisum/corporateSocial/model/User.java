package com.nisum.corporateSocial.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class User {
	String userId;
	String password;
	String userName ;
	String firstName;
	String middleName;
	String lastName;
	String email;
	String connectionSite;
	
}
