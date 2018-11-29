package com.unimitra.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.EventDao;
import com.unimitra.entity.EventsEntity;
import com.unimitra.service.EventsService;

@Service
@Transactional
public class EventsServiceImpl implements EventsService {

	@Autowired
	EventDao eventsDao;

	@Override
	public List<EventsEntity> getEventDetails() {
		return eventsDao.getEventDetails();
	}

}
