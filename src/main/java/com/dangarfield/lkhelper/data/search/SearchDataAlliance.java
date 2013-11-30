package com.dangarfield.lkhelper.data.search;

import java.util.List;

public class SearchDataAlliance extends AbstractSearchData {

	private List<TypeaheadAlliance> results;

	/**
	 * @param query
	 * @param skip
	 * @param limit
	 * @param page
	 */
	public SearchDataAlliance(String serverId, String query, int skip, int limit, int page, int displayFrom) {
		super(serverId, query, skip, limit, page, displayFrom);
	}

	public List<TypeaheadAlliance> getResults() {
		return results;
	}

	public void setResults(List<TypeaheadAlliance> results) {
		this.results = results;
	}
}
