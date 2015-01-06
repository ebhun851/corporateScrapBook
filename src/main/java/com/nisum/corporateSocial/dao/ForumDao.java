package com.nisum.corporateSocial.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.nisum.corporateSocial.model.Forum;
import com.nisum.corporateSocial.model.ForumMessage;
import com.nisum.corporateSocial.model.User;

public class ForumDao {
	
	 @Autowired
	 private DataSource dataSource;
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 @Autowired
	 private UserDao userDao;
	 private static final String FORUM_BY_ID = "select * from forum where forum_id = ?";
	 private static final String FORUM_BY_OWNER_USER_ID = "select * from forum where logged_user_id = ?";
	 private static final String FORUM_BY_OWNER_INTEREST = "select * from forum where interest_category_id IN " +
	 		"(select interest_id from INTREST_DTL where user_id = ?)";
	 
	 private static final String MESSAGES_BY_FORUM_ID = "select * from MESSAGE where forum_id = ?";
	 private static final String CREATE_MESSAGE = "insert into MESSAGE(forum_id,logged_user_id,message_content,time_stamp) " +
	 		"values(?,?,?,?)";
	 private static final String CREATE_FORUM = "insert into FORUM(forum_id,owner_user_id,forum_title,time_stamp," +
	 		"interest_category_id) values(?,?,?,?,?)";
	 
	
	public Forum getForumByForumId(String forumId){
		 Forum forum = jdbcTemplate.queryForObject(FORUM_BY_ID,new Object[]{forumId}, new ForumMapper());
		 return forum ;
	}
	
	public List<ForumMessage> getForumMessagesByForumId(String forumId){
		return jdbcTemplate.query(MESSAGES_BY_FORUM_ID,new Object[]{forumId}, new MessageMapper());
		
	}
	
	public void saveForumMessage(ForumMessage message){
		jdbcTemplate.update(CREATE_MESSAGE, new Object[]{message.getForumId(),message.getForumMessageCreator().getUserId(),
				message.getMessageContent(),new DateTime().toString()});
	}
	
	public List<Forum> getForumsByOwnerUserName(String userName){
		User user = userDao.getUserByUserName(userName);
		return jdbcTemplate.query(FORUM_BY_OWNER_USER_ID,new Object[]{user.getUserId()}, new ForumMapper());
	}
	
	public List<Forum> getForumsByUserInterest(String userName){
		User user = userDao.getUserByUserName(userName);
		return jdbcTemplate.query(FORUM_BY_OWNER_INTEREST,new Object[]{user.getUserId()}, new ForumMapper());
	}
	
	public int saveForum(Forum forum){
		return jdbcTemplate.update(CREATE_FORUM, new Object[]{forum.getForumOwnerId(),forum.getForumTitle()
				,new DateTime().toString(),forum.getCategoryId()});
	}
	
	
	
	class ForumMapper implements RowMapper<Forum> {
		   public Forum mapRow(ResultSet rs, int rowNum) throws SQLException {
		      Forum forum = new Forum();
		      forum.setForumId(rs.getString("forum_id"));
		      forum.setForumOwnerId(rs.getString("forum_owner_user_id"));
		      forum.setForumTitle(rs.getString("forum_title"));
		      forum.setTimeStamp(new DateTime(rs.getString("time_stamp")));
		      return forum;
		   }
	}
	
	class MessageMapper implements ParameterizedRowMapper<ForumMessage> {
		   public ForumMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			  ForumMessage forumMessage = new ForumMessage();
		      User user = new User();
		      forumMessage.setForumId(rs.getString("forum_id"));
		      forumMessage.setMessageContent(rs.getString("message_content"));
		      user.setUserId(rs.getString("logged_user_id"));
		      forumMessage.setForumMessageCreator(user);
		      forumMessage.setTimeStamp(new DateTime(rs.getString("time_stamp")));
		      return forumMessage;
		   }
	}
	
}
