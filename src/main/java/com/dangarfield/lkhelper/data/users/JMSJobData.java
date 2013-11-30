package com.dangarfield.lkhelper.data.users;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JMSJobData {

	@JsonProperty("_id")
	private String id;
	private String userId;
	private String correlationId;
	private int playerId;
	private String serverId;
	private Date startTime;
	private Date endTime;
	private String detail;
	private int step;
	private JMSJobStatus status;
	private JMSJobType jobType;
	
	public enum JMSJobStatus {
		QUEUED,
		RUNNING,
		COMPLETE,
		ERROR
	}
	public enum JMSJobType {
		AUTHENTICATE_USER,
		GATHER_ALL,
		GATHER_PLAYER
	}


	/**
	 * 
	 */
	public JMSJobData() {
		super();
	}

	/**
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param detail
	 * @param status
	 */
	public JMSJobData(final String id, final String userId, final String correlationId, final JMSJobType jobType, final int playerId, final String serverId, final Date startTime, final String detail) {
		super();
		this.id = id;
		this.userId = userId;
		this.correlationId = correlationId;
		this.playerId = playerId;
		this.serverId = serverId;
		this.startTime = new Date();
		this.endTime = null;
		this.detail = detail;
		this.step = 1;
		this.status = JMSJobStatus.QUEUED;
		this.jobType = jobType;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}


	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}


	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}


	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}


	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}


	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}


	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}


	/**
	 * @return the status
	 */
	public JMSJobStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(JMSJobStatus status) {
		this.status = status;
	}


	/**
	 * @return the jobType
	 */
	public JMSJobType getJobType() {
		return jobType;
	}


	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(JMSJobType jobType) {
		this.jobType = jobType;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}


	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}


	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}


	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

}
