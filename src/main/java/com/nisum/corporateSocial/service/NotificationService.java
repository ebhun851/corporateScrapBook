package com.nisum.corporateSocial.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.NotificationDao;
import com.nisum.corporateSocial.model.Notification;

@Getter
@Setter
@Service
public class NotificationService {
	@Autowired(required=true)
	private NotificationDao notificationDao;
	
	@Autowired(required=true)
	private Notification notification;
	
	public List<Notification> getNotifications(String userName){
		List <Notification> notifications= null;
		synchronized(this)
		{
			notifications = notificationDao.getUnreadNotificationsByUserName(userName);
			notificationDao.markNotificationsAsRead(notifications);
		}
		
		return notifications;
	}
	
	public void deleteNotifications(){
		
	}
	
	public void addNotification(Notification notification){
		
	}
	
	
	
	
	
	
}
