package com.unimitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.entity.EventsEntity;
import com.unimitra.service.EventsService;

@RestController
@RequestMapping("/events")
public class EventController {

	private static final Logger LOGGER = Logger.getLogger(EventController.class);
	@Autowired
	EventsService eventsService;

	@GetMapping("/get-details")
	public List<EventsEntity> getEventDetails() {
		List<EventsEntity> eventList = eventsService.getEventDetails();
		LOGGER.info(eventList.toString());
		return ObjectUtils.isEmpty(eventList) ? new ArrayList<>() : eventList;
	}

}
