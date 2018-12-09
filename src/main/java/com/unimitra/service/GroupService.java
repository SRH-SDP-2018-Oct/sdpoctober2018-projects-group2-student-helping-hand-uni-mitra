package com.unimitra.service;

import org.springframework.stereotype.Service;
import com.unimitra.entity.GroupEntity;

@Service
public interface GroupService {


	String decideGroupService(GroupEntity groupEntity);

}
