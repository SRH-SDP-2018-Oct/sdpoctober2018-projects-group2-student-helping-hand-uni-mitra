package com.unimitra.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.unimitra.exception.UnimitraException;
import com.unimitra.service.EventsService;
import com.unimitra.utility.UnimitraConstants;

@RestController
@RequestMapping("/events")
public class EventController {

	private static final Logger LOGGER = LogManager.getLogger();
	@Autowired
	EventsService eventsService;

	@GetMapping("/get-all-details")
	public List<EventsEntity> getAllEventDetails() throws UnimitraException {
		List<EventsEntity> eventList = eventsService.getEventDetails();
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + eventList.toString());
		return ObjectUtils.isEmpty(eventList) ? new ArrayList<>() : eventList;
	}

	@GetMapping("/get-details-by-id")
	public EventsEntity getEventDetail(@RequestParam int eventId) throws UnimitraException {
		EventsEntity eventByEventId = eventsService.getEventDetailById(eventId);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + eventByEventId.toString());
		return eventByEventId;

	}
	
	@PostMapping("/editEvent")
	public EventsEntity editEvent(@RequestBody EventsEntity editEvent, @RequestParam int userId) throws UnimitraException {
		EventsEntity event = eventsService.editEvent(editEvent,userId);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + event.toString());
		return event;

	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteEvent(@RequestParam int eventId, @RequestParam int createdByuserId)
			throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + "eventId {}, createdByuserId {}", eventId, createdByuserId);
		return eventsService.deleteEventById(eventId, createdByuserId);

	}

	@PostMapping("/postEvent")
	public EventsEntity postEvent(@RequestBody EventsEntity postEvent) {
		EventsEntity event = eventsService.postEvent(postEvent);
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + event.toString());
		return event;

	}

	@PostMapping("/registerForEvent")
	public ResponseEntity<String> registerForEvent(@RequestBody EventsRegisterationEntity registerForEvent)
			throws UnimitraException {
		LOGGER.info(UnimitraConstants.UNI_MITRA_AUDIT + registerForEvent.toString());
		return eventsService.registrationForEvent(registerForEvent);

	}

}
