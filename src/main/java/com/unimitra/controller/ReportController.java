package com.unimitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unimitra.exception.UnimitraException;
import com.unimitra.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@GetMapping("/generate-discussion-report")
	public String generateDiscussionReport(@RequestParam Integer userId) throws UnimitraException {
		return reportService.generateDiscussionReport(userId);
	}
}
