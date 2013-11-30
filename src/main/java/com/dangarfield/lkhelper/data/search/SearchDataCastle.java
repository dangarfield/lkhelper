package com.dangarfield.lkhelper.data.search;

import java.util.List;

public class SearchDataCastle extends AbstractSearchData {

	private List<TypeaheadCastle> results;

	/**
	 * @param query
	 * @param skip
	 * @param limit
	 * @param page
	 */
	public SearchDataCastle(String serverId, String query, int skip, int limit, int page, int displayFrom) {
		super(serverId, query, skip, limit, page, displayFrom);
	}

	public List<TypeaheadCastle> getResults() {
		return results;
	}

	public void setResults(List<TypeaheadCastle> results) {
		this.results = results;
	}
}
