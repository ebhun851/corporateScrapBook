package com.nisum.corporateSocial.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.UserProfile;

@Repository
@Getter
@Setter
public class UserDao {
	 @Autowired
	 private DataSource dataSource;
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	 private static final String  INSERT_QUERY = "insert into users (user_name, first_name,middle_name,last_name,email,password,connection_site) values (?, ?,?,?,?,?,?)";
	 private static final String SELECT_QUERY = "select * from users where username = ?";
	 
	public User getUserByUserName(String userName){
	    User user = jdbcTemplate.queryForObject(SELECT_QUERY,new Object[]{userName}, new UserMapper());
	    return user;
	}
	
		
	public void insertUserProfile(UserProfile userProfile){
		
	}
	
	
	public User insertUser(User user){
	     jdbcTemplate.update(INSERT_QUERY,new Object[]{user.getUserName()}, new UserMapper());
	    return user;
	}
	
	public void modifyUserProfile(UserProfile userProfile){
		
	}	
	
	class UserMapper implements RowMapper<User> {
		   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		      User user = new User();
		      user.setUserId(rs.getString("userId"));
		      user.setUserName(rs.getString("userName"));
		      user.setPassword(rs.getString("password"));
		      return user;
		   }
		}
	
}
