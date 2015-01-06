package com.nisum.corporateSocial.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.User;


@Getter
@Setter
@Repository
public class UserDao {
	 @Autowired
	 private DataSource dataSource;
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	
	 private static final String  INSERT_QUERY = "insert into users (user_name, first_name,middle_name,last_name,email," +
	 		"password,connection_site) values (?, ?,?,?,?,?,?)";
	 private static final String  MODIFY_QUERY = "update users set  first_name = ?,middle_name = ?,last_name = ?,email = ?," +
	 		"password = ?,connection_site = ?) where user_name = ?";
	 private static final String USER_BY_USER_NAME = "select * from USERS where user_name = ?";
	 private static final String USER_BY_USER_ID = "select * from USERS where user_id = ?";
	public User getUserByUserName(String userName){
	    User user = jdbcTemplate.queryForObject(USER_BY_USER_NAME,new Object[]{userName}, new UserMapper());
	    return user;
	}
	
	public User getUserByUserId(String userId){
	    User user = jdbcTemplate.queryForObject(USER_BY_USER_ID,new Object[]{userId}, new UserMapper());
	    return user;
	}
	
	public int insertUser(User user){
	  return  jdbcTemplate.update(INSERT_QUERY,new Object[]{user.getUserName(), user.getFirstName(),user.getMiddleName(),
			  user.getLastName(),user.getEmail(),user.getPassword(),user.getConnectionSite()});
	}
		
	public int modifyUser(User user){
		User modifiedUser = modifyUserDetails(user);
		return  jdbcTemplate.update(MODIFY_QUERY,new Object[]{ modifiedUser.getFirstName(),modifiedUser.getMiddleName(),
				modifiedUser.getLastName(),modifiedUser.getEmail(),modifiedUser.getPassword(),
				modifiedUser.getConnectionSite(), modifiedUser.getUserName()});
	}	
	
	private User modifyUserDetails(User user){
		User  oldUserDtls = getUserByUserName(user.getUserName());
		user.setConnectionSite(user.getConnectionSite()==null? oldUserDtls.getConnectionSite():user.getConnectionSite());
		user.setEmail(user.getEmail()==null? oldUserDtls.getEmail():user.getEmail());
		user.setFirstName(user.getFirstName()==null? oldUserDtls.getFirstName():user.getFirstName());
		user.setMiddleName(user.getMiddleName()==null? oldUserDtls.getMiddleName():user.getMiddleName());
		user.setLastName(user.getLastName()==null? oldUserDtls.getLastName():user.getLastName());
		user.setPassword(user.getPassword()==null? oldUserDtls.getPassword():user.getPassword());
		return user;
	}
	
	class UserMapper implements RowMapper<User> {
		   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		      User user = new User();
		      user.setUserId(rs.getString("user_id"));
		      user.setUserName(rs.getString("user_name"));
		      user.setPassword(rs.getString("password"));
		      user.setFirstName(rs.getString("first_name"));
		      user.setMiddleName(rs.getString("middle_name"));
		      user.setLastName(rs.getString("last_name"));
		      user.setEmail(rs.getString("email_id"));
		      user.setConnectionSite(rs.getString("connection-site"));
		      return user;
		   }
	}
}
