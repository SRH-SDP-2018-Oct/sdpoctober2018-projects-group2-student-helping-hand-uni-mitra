package com.unimitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.entity.EventsEntity;
import com.unimitra.entity.EventsRegisterationEntity;
import com.unimitra.service.EventsService;

@RestController
@RequestMapping("/events")
public class EventController {

	private static final Logger LOGGER = LogManager.getLogger();
	@Autowired
	EventsService eventsService;

	@GetMapping("/get-all-details")
	public List<EventsEntity> getAllEventDetails() {
		List<EventsEntity> eventList = eventsService.getEventDetails();
		LOGGER.info("UNIMITRA-AUDIT " + eventList.toString());
		return ObjectUtils.isEmpty(eventList) ? new ArrayList<>() : eventList;
	}

	@GetMapping("/get-details-by-id")
	public EventsEntity getEventDetail(@RequestParam int eventId) {
		 return eventsService.getEventDetailById(eventId);
		
	}

	@DeleteMapping("/delete")
	public String deleteEvent(@RequestParam int eventId) {
		return eventsService.deleteEventById(eventId);
		
	}

	@PostMapping("/postEvent")
	public EventsEntity postEvent(@RequestBody EventsEntity postEvent) {
		return eventsService.postEvent(postEvent);

		}
	
	@PostMapping("/registerForEvent")
	public String registerForEvent(@RequestBody EventsRegisterationEntity registerForEvent) {
		eventsService.registrationForEvent(registerForEvent);
		return "200";
	
	}
	
}
