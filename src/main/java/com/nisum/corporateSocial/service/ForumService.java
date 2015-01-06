package com.nisum.corporateSocial.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.ForumDao;
import com.nisum.corporateSocial.dao.InterestDao;
import com.nisum.corporateSocial.dao.UserDao;
import com.nisum.corporateSocial.model.Forum;
import com.nisum.corporateSocial.model.ForumMessage;
import com.nisum.corporateSocial.model.Notification;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;

@Getter
@Setter
@Service
public class ForumService {
	@Autowired(required=true)
	private ForumDao forumDao ;
	@Autowired(required=true)
	private InterestDao interestDao;
	@Autowired(required=true)
	private NotificationService notificationService;
	@Autowired(required=true)
	private WallPostService wallPostService ;
	@Autowired(required=true)
	private UserDao userDao ;
	@Autowired(required=true)
	private FollowService followService;
	
	
	public Forum getForum(String forumId){
		return forumDao.getForumByForumId(forumId);
	}
	
	public List<ForumMessage> getForumMessages(String forumId){
		return forumDao.getForumMessagesByForumId(forumId);
	}
	
	
	public void postForumMessage(ForumMessage message){
		forumDao.saveForumMessage(message);
		Forum forum = forumDao.getForumByForumId(message.getForumId());
		//creator needs to be notified and posted on his wall.
		notifyOwner(forum , message);
		//all the people who are following that forum needs to be notified and posted on his wall.
		List<User> usersFollowingForum = followService.getAllUsersFollowingForum(forum);
		
		//all the people who are following the person who has posted that message need to be posted on their wall about the
		//message
		
		//all the people who have posted messages on that forum needs to posted on their wall about the message
	}

	private void notifyOwner(Forum forum, ForumMessage message) {
		User forumOwner = userDao.getUserByUserId(forum.getForumOwnerId());
		Notification notification = createNotificationForForumPost(forumOwner,forum);
		List<User> notifiers = new ArrayList<User>();
		notifiers.add(forumOwner);
		notificationService.addNotification(notifiers, notification);
	}
	
	public List<Forum> getAllForumsAccordingUserInterests(String userName){
		return forumDao.getForumsByUserInterest(userName);
	}
	
	public List<Forum> getAllForumsCreatedByUser(String userName){
		return forumDao.getForumsByOwnerUserName(userName);
	}
	
	public boolean createForum(String forumOwnerUserName, Forum forum){
		User user = userDao.getUserByUserName(forumOwnerUserName);
		forum.setForumOwnerId(user.getUserId());
		boolean success = forumDao.saveForum(forum)==0?false:true;
		if(success){
			List<User> followingUsers = followService.getAllUsersFollowingUser(user);
			Notification notification = createNotificationForForumCreation(user, forum);
			notificationService.addNotification(followingUsers, notification);
			WallPost wallPost = createWallPostForFollowers(user, forum);
			wallPostService.addPostForUserWall(followingUsers,wallPost);
		}
		return success;
	}
	
	private Notification createNotificationForForumCreation(User user, Forum forum){
		Notification notification = new Notification();
		notification.setNotificationContent(user.getFirstName()+" created a forum");
		notification.setReadStatus(false);
		notification.setForumMessageId(forum.getForumId());
		return notification;
	}
	
	private Notification createNotificationForForumPost(User user, Forum forum){
		Notification notification = new Notification();
		notification.setNotificationContent(user.getFirstName()+" posted message on a forum");
		notification.setReadStatus(false);
		notification.setForumMessageId(forum.getForumId());
		return notification;
	}
	
	private WallPost createWallPostForFollowers(User user,Forum forum){
		WallPost wallPost = new WallPost();
		wallPost.setPostContent(user.getFirstName()+" created a forum with the " +
				"subject "+forum.getForumTitle() + " at "+ forum.getTimeStamp());
		wallPost.setTimeStamp(forum.getTimeStamp());
		wallPost.setWallPostUser(user);
		return wallPost;
	}
}
