package com.unimitra.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.unimitra.dao.ReportDao;
import com.unimitra.model.DiscussionReportModel;

@Repository
public class ReportDaoImpl implements ReportDao {

	SessionFactory sessionFactory;

	@Override
	public List<DiscussionReportModel> getDiscussionReportData() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")

		List<Object[]> source=(List<Object[]>) session.createNativeQuery("select que.question_description,que.question_posted_by_user,que.question_creation_date_time,ans.answer_description,ans.answer_posted_by_user,ans.answer_date_time, ans.answer_status from \r\n" + 
				"(select question_description, (first_name || ' ' || last_name) as question_posted_by_user, question_creation_date_time,question_id \r\n" + 
				"from questions q,user_details ud\r\n" + 
				"where ud.user_id = q.question_Posted_by_user_id) que\r\n" + 
				"left outer join\r\n" + 
				"(select answer_description,(first_name || ' ' || last_name) as answer_posted_by_user,answer_date_time, a.answer_status,question_id\r\n" + 
				"from answers a,user_details ud\r\n" + 
				"where ud.user_id = a.answer_Posted_by_user_id) ans on que.question_id = ans.question_id order by que.question_id ;").getResultList();
		List<DiscussionReportModel> listdiscussionReportModel = new ArrayList<DiscussionReportModel>(source.size());
		for (Object[] target:source) {
			DiscussionReportModel discussionReportModel = new DiscussionReportModel((String)target[0],(String)target[1],(Date)target[2],(String)target[3],(String)target[4],(Date)target[5],(String)target[6]);
			listdiscussionReportModel.add(discussionReportModel);
		}
		return listdiscussionReportModel;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
