package com.sameer.covidtracker.entity;

public class CovidReportCountryStates {

	private String Country = new String();
	
	private String State = new String();

	public CovidReportCountryStates() {
		super();
	}

	public CovidReportCountryStates(String country, String state) {
		super();
		Country = country;
		State = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return Country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		Country = country;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return State;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		State = state;
	}

	@Override
	public String toString() {
		return "CovidReportCountryStates [Country=" + Country + ", State=" + State + "]";
	}
	
	
}
