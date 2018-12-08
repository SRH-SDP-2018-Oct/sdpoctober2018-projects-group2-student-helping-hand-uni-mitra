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

@Setter
@Getter
@ToString
@Entity
@Table(name = "groups")
public class GroupEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int groupId;
	
	@Column(name = "group_name")
	private String groupName;
	
	@Column(name = "group_description")
	private String groupDescription;
	
	@Column(name = "group_created_by_user_id")
	private int groupCreatedByUserId;
	
	@Column(name = "group_creation_date_time")
	private Date groupCreationDatetime;
	
	@Column(name = "group_is_active")
	private boolean groupIsActive;
	
	@Column(name = "group_approval_status")
	private String groupApprovalStatus;
	
	@Column(name = "group_approval_by_user_id")
	private int groupApprovalByUserId;

	@Column(name = "group_category_id")
	private int groupCategoryId;
}
