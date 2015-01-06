package com.nisum.corporateSocial.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class Interest {
	private String interestId;
	private String interestType;
	private String interstName;
	private String userId;
}
