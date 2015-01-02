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
import org.springframework.web.bind.annotation.RequestParam;

import com.nisum.corporateSocial.model.LoggedInUsers;
import com.nisum.corporateSocial.model.User;
import com.nisum.corporateSocial.model.WallPost;
import com.nisum.corporateSocial.service.AuthenticationService;
import com.nisum.corporateSocial.service.ForumService;
import com.nisum.corporateSocial.service.UserProfileService;

@Getter
@Setter
@Controller
public class ForumController {
	
	@Autowired
	private AuthenticationService authenticationService ;
	@Autowired
	private UserProfileService userProfileService ;
	@Autowired
	private LoggedInUsers loggedInUsers ;  
	
	@Autowired
	private ForumService forumService;
	
	
	@RequestMapping(value = "/forum/createForum", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<WallPost>> signInAuthentication(@RequestBody(required = true)
         User user) throws IOException {
			if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
//				List<WallPost> wallPosts = wallPostService.getWallPosts(user.getUserName());
//				return new ResponseEntity<List<WallPost>>(wallPosts, HttpStatus.OK);
			}
			return new ResponseEntity<List<WallPost>>(new ArrayList<WallPost>(), HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/forum/{forumId}/", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<WallPost>> getForum(
			   @RequestParam(required=true)String forumId) throws IOException {
			/*if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
//				List<WallPost> wallPosts = wallPostService.getWallPosts(user.getUserName());
//				return new ResponseEntity<List<WallPost>>(wallPosts, HttpStatus.OK);
			}*/
			return new ResponseEntity<List<WallPost>>(new ArrayList<WallPost>(), HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/forums", method = RequestMethod.GET, consumes ="application/json")
	   public ResponseEntity<List<WallPost>> getForums(@RequestBody(required = true)
       User user) throws IOException {
			if(user!=null & loggedInUsers.userAlreadyLoggedIn(user.getUserId())){
//				List<Forum> wallPosts = forumService.getForumsForUser(user.getUserName());
//				return new ResponseEntity<List<WallPost>>(wallPosts, HttpStatus.OK);
			}
			return new ResponseEntity<List<WallPost>>(new ArrayList<WallPost>(), HttpStatus.UNAUTHORIZED);
	}
	
	
	
	
	
	
}
