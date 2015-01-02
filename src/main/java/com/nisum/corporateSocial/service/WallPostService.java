package com.nisum.corporateSocial.service;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

import com.nisum.corporateSocial.dao.WallPostingsDao;
import com.nisum.corporateSocial.model.WallPost;


@Getter
@Setter
@Service
public class WallPostService {
	@Autowired(required=true)
	private WallPostingsDao wallPostingDao;
	
	
	public List<WallPost> getWallPosts(String userName) throws IOException{
		return wallPostingDao.getWallPostsByUserName(userName);
	}
	
}
