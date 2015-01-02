package com.nisum.corporateSocial.service;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.UserDao;
import com.nisum.corporateSocial.model.User;

@Getter
@Setter
@Service
public class UserProfileService {
	@Autowired
	private UserDao userDao;
	
	public boolean saveUser(User user){
		return (userDao.insertUser(user))!=null?true:false;
		
	}
}
