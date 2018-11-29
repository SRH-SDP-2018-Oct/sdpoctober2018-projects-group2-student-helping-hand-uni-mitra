package com.unimitra.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupModel {
	private int groupId;
	private String groupName;
	private String groupDescription;
	private String createdBy;
	private String category;
	private Date creationDate;
	private String approvalStatus;
	
	
}
