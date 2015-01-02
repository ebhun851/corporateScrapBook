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
public class AuthenticationService {
		@Autowired
		private UserDao userDao;
		
		public boolean authenticate(User user){
			String dbPassword = userDao.getUserByUserName(user.getUserName()).getPassword();
			return user.getPassword().equalsIgnoreCase(dbPassword)? true:false;
		}
		
		public boolean checkAlreadyRegiestered(User user){
			return (userDao.getUserByUserName(user.getUserName()))!=null?true:false;
		}
}
