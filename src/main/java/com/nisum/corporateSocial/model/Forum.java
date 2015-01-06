package com.nisum.corporateSocial.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Forum {
	private String forumId;
	private String forumOwnerId;
	private String forumTitle;
	private DateTime timeStamp;
	private String categoryId;
	private List<ForumMessage> forumMessages;
}
