package com.unimitra.service;

import com.unimitra.exception.UnimitraException;

public interface ReportService {

	public String generateDiscussionReport(Integer userId) throws UnimitraException;
}
