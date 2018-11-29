package com.unimitra.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupMemberEntity {
	
	private int groupMemberId;
	private int memberUserId;
	private int memberGroupId;
	private Date creationDateTime;
	private boolean isGroupMemberActive;

}
