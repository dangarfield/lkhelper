package com.dangarfield.lkhelper.data.search;

public class TypeaheadPlayer extends AbstractTypeaheadData{
	
	private String alliance;
	
	/**
	 * 
	 */
	public TypeaheadPlayer() {
		super();
	}
	/**
	 * @param value
	 * @param name
	 * @param alliance
	 */
	public TypeaheadPlayer(String value, int id, String alliance) {
		super(value, id);
		this.alliance = alliance;
	}
	/**
	 * @return the alliance
	 */
	public String getAlliance() {
		return alliance;
	}
	/**
	 * @param alliance the alliance to set
	 */
	public void setAlliance(String alliance) {
		this.alliance = alliance;
	}
}
