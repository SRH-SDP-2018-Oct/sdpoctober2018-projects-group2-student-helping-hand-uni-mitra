package com.unimitra.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unimitra.entity.CategoryEntity;
import com.unimitra.entity.GroupEntity;
import com.unimitra.exception.UnimitraException;

public interface GroupDao {

	GroupEntity createGroup(GroupEntity groupEntity);

	ResponseEntity<String> deleteGroupbyGroupId(int groupId) throws UnimitraException;

	boolean checkIfUserHasAccessToGroup(int userId, int groupId) throws UnimitraException;

	List<CategoryEntity> getListOfCategoryByCategoryName(String categoryName) throws UnimitraException;

	List<GroupEntity> getGroupListByCategoryIds(List<Integer> categoryListIds) throws UnimitraException;

	List<GroupEntity> getGroupListByGroupName(String groupName) throws UnimitraException;


}
