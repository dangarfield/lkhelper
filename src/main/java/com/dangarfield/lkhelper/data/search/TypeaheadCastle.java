package com.dangarfield.lkhelper.data.search;

public class TypeaheadCastle extends AbstractTypeaheadData{
	
	private String player;
	private String alliance;
	
	/**
	 * 
	 */
	public TypeaheadCastle() {
		super();
	}
	/**
	 * @param value
	 * @param name
	 * @param alliance
	 */
	public TypeaheadCastle(String value, int id, String player, String alliance) {
		super(value, id);
		this.player = player;
		this.alliance = alliance;
	}
	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(String player) {
		this.player = player;
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
