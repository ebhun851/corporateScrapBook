package com.nisum.corporateSocial.dao;

import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nisum.corporateSocial.model.WallPost;

@Getter
@Setter
@Repository
public class WallPostingsDao {
	
	@Autowired(required=true)
	private WallPost wallPosting;
	
	
	public List<WallPost> getWallPostsByUserName(String userName) throws IOException{
		return null;
	}
}
