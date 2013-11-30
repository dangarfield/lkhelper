package com.dangarfield.lkhelper.data.search;

import java.util.List;

public class SearchDataPlayer extends AbstractSearchData {

	private List<TypeaheadPlayer> results;

	/**
	 * @param query
	 * @param skip
	 * @param limit
	 * @param page
	 */
	public SearchDataPlayer(String serverId, String query, int skip, int limit, int page, int displayFrom) {
		super(serverId, query, skip, limit, page, displayFrom);
	}

	public List<TypeaheadPlayer> getResults() {
		return results;
	}

	public void setResults(List<TypeaheadPlayer> results) {
		this.results = results;
	}
}
