package com.nisum.corporateSocial.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
public class WallPost {
	private String postId;
	private String postContent;
	private DateTime timeStamp;
	private User wallOwner;
	private User wallPostUser;
	private Type type;
	
}
