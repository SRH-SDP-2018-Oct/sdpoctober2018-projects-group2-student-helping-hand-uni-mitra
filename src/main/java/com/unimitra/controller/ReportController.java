package com.unimitra.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> generateDiscussionReport(@RequestParam Integer userId) throws UnimitraException,IOException {
		String discussionReportPDF=reportService.generateDiscussionReport(userId);
		File file=new File(discussionReportPDF);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		return responseEntity;

	}
}
