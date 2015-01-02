package com.nisum.corporateSocial.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nisum.corporateSocial.model.LoggedInUsers;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;
import com.nisum.corporateSocial.service.WallPostService;


@Getter
@Setter
@Controller
public class WallPostController {
	@Autowired
	private LoggedInUsers loggedInUsers ;  
	@Autowired
	private User user;
	@Autowired
	private WallPostService wallPostService;
	
	@RequestMapping(value = "/retrieve/wallPost", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<WallPost>> signInAuthentication(@RequestBody(required = true)
            User user) throws IOException {
			if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
				List<WallPost> wallPosts = wallPostService.getWallPosts(user.getUserName());
				return new ResponseEntity<List<WallPost>>(wallPosts, HttpStatus.OK);
			}
			return new ResponseEntity<List<WallPost>>(new ArrayList<WallPost>(), HttpStatus.UNAUTHORIZED);
	}
}
