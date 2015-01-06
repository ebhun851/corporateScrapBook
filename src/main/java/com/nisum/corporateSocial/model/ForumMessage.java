package com.nisum.corporateSocial.model;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ForumMessage {
	
	private User forumMessageCreator;
	private String messageId;
	private String forumId;
	private String messageContent;
	private DateTime timeStamp;
}
