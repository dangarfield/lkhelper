package com.dangarfield.lkhelper.data.search;

public abstract class AbstractSearchData {
	
	private String serverId;
	private String query;
	private int skip;
	private int limit;
	private int page;
	
	private int displayFrom;
	private int displayTo;
	private int totalPages;
	private int totalResults;
	
	
	
	/**
	 * @param skip
	 * @param limit
	 * @param page
	 */
	public AbstractSearchData(String serverId, String query, int skip, int limit, int page, int displayFrom) {
		super();
		this.serverId = serverId;
		this.query = query;
		this.skip = skip;
		this.limit = limit;
		this.page = page;
		this.displayFrom = displayFrom;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the skip
	 */
	public int getSkip() {
		return skip;
	}
	/**
	 * @param skip the skip to set
	 */
	public void setSkip(int skip) {
		this.skip = skip;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the displayFrom
	 */
	public int getDisplayFrom() {
		return displayFrom;
	}
	/**
	 * @param displayFrom the displayFrom to set
	 */
	public void setDisplayFrom(int displayFrom) {
		this.displayFrom = displayFrom;
	}
	/**
	 * @return the displayTo
	 */
	public int getDisplayTo() {
		return displayTo;
	}
	/**
	 * @param displayTo the displayTo to set
	 */
	public void setDisplayTo(int displayTo) {
		this.displayTo = displayTo;
	}
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @return the totalResults
	 */
	public int getTotalResults() {
		return totalResults;
	}
	/**
	 * @param totalResults the totalResults to set
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
}
