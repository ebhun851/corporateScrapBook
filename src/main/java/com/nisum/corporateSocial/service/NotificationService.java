package com.nisum.corporateSocial.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.NotificationDao;
import com.nisum.corporateSocial.dao.UserDao;
import com.nisum.corporateSocial.model.Notification;
import com.nisum.corporateSocial.model.User;

@Getter
@Setter
@Service
public class NotificationService {
	@Autowired(required=true)
	private NotificationDao notificationDao;
	@Autowired(required=true)
	private Notification notification;
	@Autowired(required=true)
	private UserDao userDao;
	
	public List<Notification> getUnreadNotifications(String userName){
		User user = userDao.getUserByUserName(userName);
		List <Notification> unreadNotifications = notificationDao.getNotificationsByUserIdAndStatus(user.getUserId(),Boolean.FALSE);
		notificationDao.markNotificationsAsRead(unreadNotifications);
		return unreadNotifications;
	}
	
	public List<Notification> getReadNotifications(String userName){
		User user = userDao.getUserByUserName(userName);
		List <Notification> readNotifications = notificationDao.getNotificationsByUserIdAndStatus(user.getUserId(),Boolean.TRUE);
		return readNotifications;
	}
	
	public List<Notification> getAllNotifications(String userName){
		User user = userDao.getUserByUserName(userName);
		List <Notification> notifications = notificationDao.getAllNotificationsByUserId(user.getUserId());
		return notifications;
	}
	
	public int deleteNotifications(){
		return 0;
	}
	
	public void addNotification(List<User>users, Notification notification){
		notificationDao.addNotificationForUsers(users,notification);
	}
}
