package com.nisum.corporateSocial.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Component
public class Notification {
	private String notificationId;
	private String wallPostId;
	private String forumMessageId;
	private boolean readStatus;
	private String notificationContent;
}
