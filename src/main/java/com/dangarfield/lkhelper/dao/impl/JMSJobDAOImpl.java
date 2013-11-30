package com.dangarfield.lkhelper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.dangarfield.lkhelper.dao.JMSJobDAO;
import com.dangarfield.lkhelper.exceptions.JMSJobNotFoundException;
import com.dangarfield.lkhelper.data.users.JMSJobData;

public class JMSJobDAOImpl implements JMSJobDAO {
	private static Logger LOG = LogManager.getLogger("JMSJobDAOImpl");
	
	private MongoCollection jmsJobCollection;

    public JMSJobDAOImpl(final Jongo jongo) {
    	jmsJobCollection = jongo.getCollection("jmsjobs");
    }

    @Override
    public void ensureIndexes() {
    	jmsJobCollection.dropIndexes();
    }

	@Override
	public List<JMSJobData> getAllJobs() {
		Iterable<JMSJobData> jobsIterable = jmsJobCollection.find().as(JMSJobData.class);
		
		List<JMSJobData> jobsDataList = new ArrayList<JMSJobData>();
		CollectionUtils.addAll(jobsDataList, jobsIterable.iterator());
		
		return jobsDataList;
	}
	
	@Override
	public List<JMSJobData> getAllRunningJobs() {
		Iterable<JMSJobData> jobsIterable = jmsJobCollection.find("{status:\"RUNNING\"}").as(JMSJobData.class);
		
		List<JMSJobData> jobsDataList = new ArrayList<JMSJobData>();
		CollectionUtils.addAll(jobsDataList, jobsIterable.iterator());
		
		return jobsDataList;
	}
	
	@Override
	public List<JMSJobData> getAllRunningJobsForUser(final String userId) {
		Iterable<JMSJobData> jobsIterable = jmsJobCollection.find("{status:\"RUNNING\", userId:\""+userId+"\"}").as(JMSJobData.class);
		
		List<JMSJobData> jobsDataList = new ArrayList<JMSJobData>();
		CollectionUtils.addAll(jobsDataList, jobsIterable.iterator());
		
		return jobsDataList;
	}



	@Override
	public JMSJobData getRunningJobData(final String id) throws JMSJobNotFoundException {
		JMSJobData serverData = jmsJobCollection.findOne("{_id: '"+id+"'}").as(JMSJobData.class);
		if (serverData == null) {
			final String error = "Job not in db: " + id;
			LOG.info(error);
			throw new JMSJobNotFoundException(error);
		}
		return serverData;
	}



	@Override
	public void saveJob(JMSJobData jobData) {
		LOG.debug("Saving jobData: " + jobData.getId());
		jmsJobCollection.save(jobData);
	}
	
	@Override
	public void removeJob(JMSJobData jobData) {
		LOG.debug("Removing jobData: " + jobData.getId());
		jmsJobCollection.remove("{_id: '"+jobData.getId()+"'}");
	}

	
}
