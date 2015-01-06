package com.nisum.corporateSocial.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class Notification {
	private String notificationId;
	private String wallPostId;
	private String forumMessageId;
	private boolean readStatus;
	private String notificationContent;
	private String userId;
	private Type type;
	private String timeStamp;
}
