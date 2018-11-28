package com.unimitra.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class GroupEntity {
	private int groupId;
	private String groupName;
	private String groupDescription;
	private String groupCreatedBy;
	private boolean isActive;
	private Date creationDate;
	private boolean approvalStatus;
	private String approvalBy;
	private String groupCategory;
}
