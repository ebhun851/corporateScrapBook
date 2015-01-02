package com.nisum.corporateSocial.dao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.Notification;
@Getter
@Setter
@Repository
public class NotificationDao {
	
	@Autowired(required=true)
	private Notification notification;
	
	public List<Notification> getUnreadNotificationsByUserName(String userName){
		return null;
	}
	
	public boolean markNotificationsAsRead(List<Notification> notifications){
		return false;
	}
}