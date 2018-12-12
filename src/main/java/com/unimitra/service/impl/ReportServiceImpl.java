package com.unimitra.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unimitra.dao.ReportDao;
import com.unimitra.dao.UserDetailsDao;
import com.unimitra.exception.ErrorCodes;
import com.unimitra.exception.UnimitraException;
import com.unimitra.model.DiscussionReportModel;
import com.unimitra.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Transactional

public class ReportServiceImpl implements ReportService {
	@Autowired
	ReportDao reportDao;

	@Autowired
	UserDetailsDao userDetailsDao;

	@Override
	public String generateDiscussionReport(Integer userId) throws UnimitraException {
		checkIfUserIsStaff(userId);
		List<DiscussionReportModel> listDiscussionReportData = reportDao.getDiscussionReportData();
		return exportReportPdf(listDiscussionReportData);
	}

	private String exportReportPdf(List<DiscussionReportModel> listDiscussionReportData) {
		
		JRBeanCollectionDataSource discussionForumsJRBean = new JRBeanCollectionDataSource(listDiscussionReportData);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("DiscussionForumDataSource", discussionForumsJRBean);
		String discussionReportPDF = "";
		try {
			String jrxmlFileName = "src/main/resources/template_DiscussionForum.jrxml";
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String userHomeDirectory = System.getProperty("user.home");
			String outputFile = userHomeDirectory + File.separatorChar + "DiscussionForumReport_" + timeStamp + ".pdf";
			OutputStream outputStream = new FileOutputStream(new File(outputFile));
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			discussionReportPDF=userHomeDirectory + File.separatorChar+ "DiscussionForumReport_" + timeStamp + ".pdf";
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return discussionReportPDF;
	}

	private void checkIfUserIsStaff(int userId) throws UnimitraException {
		if (!userDetailsDao.getUserDetails(userId).getUserType().equals("Staff")) {
			throw new UnimitraException(ErrorCodes.USER_HAS_NO_ACCESS_FOR_JAPSER_REPORT);
		}

	}

}
