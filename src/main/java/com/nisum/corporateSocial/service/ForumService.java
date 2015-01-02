package com.nisum.corporateSocial.service;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.corporateSocial.dao.ForumDao;
import com.nisum.corporateSocial.model.Forum;
import com.nisum.corporateSocial.model.ForumMessage;

@Getter
@Setter
@Service
public class ForumService {
	@Autowired
	private ForumDao forumDao ;
	
	
	public Forum getForum(String forumId){
		return forumDao.getForumById(forumId);
	}
	
	public List<ForumMessage> getForumMessages(String forumId){
		return forumDao.getForumMessagesByForumId(forumId);
	}
	
	
	public void postForumMessage(ForumMessage message){
		forumDao.postForumMessage(message);
		
	}
	
	

}
