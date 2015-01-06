package com.nisum.corporateSocial.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.Notification;
import com.nisum.corporateSocial.model.Type;
import com.nisum.corporateSocial.model.User;
@Getter
@Setter
@Repository
public class NotificationDao {
	
	@Autowired(required=true)
	private Notification notification;
	@Autowired(required=true)
	private DataSource dataSource;
	@Autowired(required=true)
	private JdbcTemplate jdbcTemplate;
	@Autowired(required=true)
	private UserDao userDao;
	
	private static final String ADD_NOTIFICATIONS = "insert into NOTIFICATION (receiver_user_id, post_id,message_id," +
			"read_status,notificaion_content,time_stamp) values (?, ?, ?, ?, ?, ?)";
	private static final String NOTIFICAIONS_BY_USER_ID_AND_STATUS = "select * from NOTIFICATION where receiver_user_id = ? AND " +
			"read_status = ?";
	private static final String NOTIFICAIONS_BY_USER_ID = "select * from NOTIFICATION where receiver_user_id = ?";
	private static final String UPDATE_NOTIFICAIONS_BY_NOTIFICATION_ID = "update read_status where notification_id = ?";
	private static final String DELETE_NOTIFICAIONS_BY_NOTIFICATION_ID = "update read_status where notification_id = ?";
	
	public List<Notification> getNotificationsByUserIdAndStatus(String userId,boolean readStatus){
		List<Notification> notifications =  jdbcTemplate.query(NOTIFICAIONS_BY_USER_ID_AND_STATUS,new Object[]{userId,readStatus}, 
				new NotificationMapper());
		for(Notification notification:notifications){
			notification.setUserId(userId);
		}
		return notifications;
	}
	
	public List<Notification> getAllNotificationsByUserId(String userId){
		List<Notification> notifications =  jdbcTemplate.query(NOTIFICAIONS_BY_USER_ID,new Object[]{userId}, 
				new NotificationMapper());
		for(Notification notification:notifications){
			notification.setUserId(userId);
		}
		return notifications;
	}
	
	public int markNotificationsAsRead(List<Notification> notifications){
		int count = 0;
		for(Notification notification:notifications){
			count += jdbcTemplate.update(UPDATE_NOTIFICAIONS_BY_NOTIFICATION_ID, new Object[]{notification.getNotificationId()});
		}
		return count;
	}
	
	public int addNotificationForUsers(List<User>users, Notification notification){
		int count = 0;
		for(User user:users){
			count += jdbcTemplate.update(ADD_NOTIFICATIONS, new Object[]{user.getUserId(),notification.getWallPostId(),
					notification.getForumMessageId(),Boolean.FALSE,notification.getNotificationContent(),
					new DateTime().toString()});
			
		}
		return count;
	}
	 
	public int deleteNotifications(){
		return 0;
	}
	
	class NotificationMapper implements ParameterizedRowMapper<Notification> {
		   public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
			  Notification notification = new Notification();
			  notification.setNotificationId(rs.getString("post_id"));
		      User wallPostUser = new User();
		      wallPostUser.setUserId(rs.getString("posting_user_id"));
		      notification.setForumMessageId(rs.getString("message_id"));
		      notification.setWallPostId(rs.getString("post_id"));
		      notification.setTimeStamp(rs.getString("time_stamp"));
		      notification.setNotificationContent(rs.getString("notificaion_content"));
		      notification.setType(notification.getForumMessageId()!=null?Type.FORUM_CREATION:Type.WALL_POST);
		      return notification;
		   }
	}
	
}