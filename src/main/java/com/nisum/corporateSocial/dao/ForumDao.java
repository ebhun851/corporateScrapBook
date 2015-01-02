package com.nisum.corporateSocial.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.Forum;
import com.nisum.corporateSocial.model.ForumMessage;

@Repository
public class ForumDao {

	private Forum forum;
	
	public Forum getForumById(String forumId){
		return null;
	}
	
	public List<ForumMessage> getForumMessagesByForumId(String forumId){
		return null;
	}
	
	
	public void postForumMessage(ForumMessage forum){
		
	}
	
}
