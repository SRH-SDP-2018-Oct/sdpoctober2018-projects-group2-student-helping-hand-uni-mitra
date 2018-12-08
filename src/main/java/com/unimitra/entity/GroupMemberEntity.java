package com.unimitra.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "group_member")

public class GroupMemberEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_member_id")
	private int groupMemberId;
	
	@Column(name = "member_user_id")
	private int memberUserId;
	
	@Column(name = "member_group_id")
	private int memberGroupId;
	
	@Column(name = "group_member_creation_date_time")
	private Date groupMemberCreationDateTime;
	
	@Column(name = "group_member_is_active")
	private boolean groupMemberIsActive;

}
