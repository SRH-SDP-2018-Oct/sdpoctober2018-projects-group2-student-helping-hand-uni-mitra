package com.unimitra.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.GroupDao;
import com.unimitra.entity.GroupEntity;
import com.unimitra.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupDao groupDao;
	
	@Override
	public String decideGroupService(GroupEntity groupEntity) {
		
	return groupDao.decideGroupStatus(groupEntity);
		
		
		
	}
	
	
	
	

}
