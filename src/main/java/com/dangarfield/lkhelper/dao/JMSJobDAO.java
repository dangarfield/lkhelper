package com.dangarfield.lkhelper.dao;

import java.util.List;

import com.dangarfield.lkhelper.exceptions.JMSJobNotFoundException;
import com.dangarfield.lkhelper.data.users.JMSJobData;

public interface JMSJobDAO {

	void ensureIndexes();
	
	List<JMSJobData> getAllJobs();

	List<JMSJobData> getAllRunningJobs();
	
	List<JMSJobData> getAllRunningJobsForUser(final String userId);
	
	JMSJobData getRunningJobData(final String id) throws JMSJobNotFoundException;
	
	void saveJob(final JMSJobData jobData);
	
	void removeJob(final JMSJobData jobData) throws JMSJobNotFoundException;
	
}

