package com.nisum.corporateSocial.model;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Forum {
	private String forumId;
	private User forumOwner;
	private String forumTitle;
	private DateTime timeStamp;
}
