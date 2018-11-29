package com.unimitra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.ManagerImpl.EventsManagerImpl;
import com.unimitra.entity.EventsEntity;
import com.unimitra.manager.EventsManager;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	EventsManager eventsManager;

	@RequestMapping(value = {
			"/getEventDetails" }, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<EventsEntity> getEventDetails() {
		List<EventsEntity> eventList = eventsManager.getEventDetails();
		if ((!(null == eventList)) || eventList.isEmpty()) {
			System.out.println("Data : " + eventList.toString());
			return eventList;
		}
		System.out.println("No data in databse");
		return null;
	}

}
