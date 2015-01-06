package com.nisum.corporateSocial.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.WallPostingsDao;
import com.nisum.corporateSocial.model.Notification;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;


@Getter
@Setter
@Service
public class WallPostService {
	@Autowired(required=true)
	private WallPostingsDao wallPostingDao;
	@Autowired(required=true)
	private FollowService followService;
	@Autowired(required=true)
	private NotificationService notificationService;
	@Autowired(required=true)
	private UserProfileService userService;
	
	public List<WallPost> getWallPosts(String userName) throws IOException{
		User user = getUser(userName); 
		return wallPostingDao.getWallPostsByUser(user);
	}
	
	
	public void putWallPost(WallPost wallPost){
		 wallPostingDao.insertWallPostForUser(wallPost);
		 Notification notification = createNotificationForWallPost(wallPost);
		 List<User> users = new ArrayList<User>();
		 User user = new User();
		 user.setUserId(notification.getUserId());
		 users.add(user);		 
		 notificationService.addNotification(users, notification);
	}
	
	public void addPostForUserWall(List<User> users, WallPost wallPost){
		wallPostingDao.insertWallPostForUsers(users,wallPost);
	}
	
	private Notification createNotificationForWallPost(WallPost post){
		Notification notification = new Notification();
		notification.setNotificationContent(post.getWallPostUser()+" has posted on your wall");
		notification.setReadStatus(false);
		notification.setWallPostId(post.getPostId());
		User user = getUser(post.getWallOwner().getUserName());
		notification.setUserId(user.getUserId());
		return notification;
	}	
	
	private User getUser(String UserName){
		return userService.getUserByUserName(UserName);
	}
}
