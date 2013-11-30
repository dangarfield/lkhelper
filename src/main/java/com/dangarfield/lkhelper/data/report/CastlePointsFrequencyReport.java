package com.dangarfield.lkhelper.data.report;

public class CastlePointsFrequencyReport implements Comparable<CastlePointsFrequencyReport>{

	private String pointsBand;
	private int frequency;
	
	
	/**
	 * 
	 */
	public CastlePointsFrequencyReport() {
		super();
	}
	/**
	 * @param points
	 * @param frequency
	 */
	public CastlePointsFrequencyReport(String pointsBand, int frequency) {
		super();
		this.pointsBand = pointsBand;
		this.frequency = frequency;
	}
	/**
	 * @return the pointsBand
	 */
	public String getPointsBand() {
		return pointsBand;
	}
	/**
	 * @param pointsBand the pointsBand to set
	 */
	public void setPoints(String pointsBand) {
		this.pointsBand = pointsBand;
	}
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	
	@Override
	public int compareTo(CastlePointsFrequencyReport o) {
		final String thisString = getPointsBand();
		final Integer thisInt = Integer.valueOf(thisString.substring(0, thisString.indexOf(" ")));
		
		final String otherString = o.getPointsBand();
		final Integer otherInt = Integer.valueOf(otherString.substring(0, otherString.indexOf(" "))); 
		
		return thisInt.compareTo(otherInt);
	}
}
