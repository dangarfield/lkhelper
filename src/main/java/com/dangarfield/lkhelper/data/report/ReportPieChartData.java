package com.dangarfield.lkhelper.data.report;

public class ReportPieChartData {
	
	private int quantity;
	private String fieldName;

	/**
	 * 
	 */
	public ReportPieChartData() {
		super();
	}

	/**
	 * @param playerId
	 * @param playerName
	 * @param lastUpdate
	 */
	public ReportPieChartData(int quantity, String fieldName) {
		super();
		this.quantity = quantity;
		this.fieldName = fieldName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String toString() {
		return this.fieldName + " - " + String.valueOf(this.quantity);
	}
}
