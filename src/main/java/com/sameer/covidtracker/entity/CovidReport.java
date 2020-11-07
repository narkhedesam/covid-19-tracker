package com.sameer.covidtracker.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Table
@Entity
public class CovidReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	
	
	private String State = "";
	private String Country = "";
	private Date lastUpdate;
	private BigInteger Confirmed = new BigInteger("0");
	private BigInteger Deaths = new BigInteger("0");
	private BigInteger Recovered = new BigInteger("0");
	private BigInteger Active= new BigInteger("0");
	private String lat = "0";
	private String long_ = "0";
	private String Combined_Key = "";
	private Double Incidence_Rate = new Double("0");
	private Double Case_Fatality_Ratio = new Double("0");
	
	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}



	/**
	 * @param lat the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}



	/**
	 * @return the long_
	 */
	public String getLong_() {
		return long_;
	}



	/**
	 * @param long_ the long_ to set
	 */
	public void setLong_(String long_) {
		this.long_ = long_;
	}



	/**
	 * @return the combined_Key
	 */
	public String getCombined_Key() {
		return Combined_Key;
	}



	/**
	 * @param combined_Key the combined_Key to set
	 */
	public void setCombined_Key(String combined_Key) {
		Combined_Key = combined_Key;
	}



	/**
	 * @return the incidence_Rate
	 */
	public Double getIncidence_Rate() {
		return Incidence_Rate;
	}



	/**
	 * @param incidence_Rate the incidence_Rate to set
	 */
	public void setIncidence_Rate(Double incidence_Rate) {
		Incidence_Rate = incidence_Rate;
	}



	/**
	 * @return the case_Fatality_Ratio
	 */
	public Double getCase_Fatality_Ratio() {
		return Case_Fatality_Ratio;
	}



	/**
	 * @param case_Fatality_Ratio the case_Fatality_Ratio to set
	 */
	public void setCase_Fatality_Ratio(Double case_Fatality_Ratio) {
		Case_Fatality_Ratio = case_Fatality_Ratio;
	}



	public CovidReport() {
		super();
	}



	public CovidReport(String state, String country, Date lastUpdate, BigInteger confirmed, BigInteger deaths,
			BigInteger recovered) {
		super();
		State = state;
		Country = country;
		this.lastUpdate = lastUpdate;
		Confirmed = confirmed;
		Deaths = deaths;
		Recovered = recovered;
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
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}



	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



	/**
	 * @return the confirmed
	 */
	public BigInteger getConfirmed() {
		return Confirmed;
	}



	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(BigInteger confirmed) {
		Confirmed = confirmed;
	}



	/**
	 * @return the deaths
	 */
	public BigInteger getDeaths() {
		return Deaths;
	}



	/**
	 * @param deaths the deaths to set
	 */
	public void setDeaths(BigInteger deaths) {
		Deaths = deaths;
	}



	/**
	 * @return the recovered
	 */
	public BigInteger getRecovered() {
		return Recovered;
	}



	/**
	 * @param recovered the recovered to set
	 */
	public void setRecovered(BigInteger recovered) {
		Recovered = recovered;
	}



	/**
	 * @return the active
	 */
	public BigInteger getActive() {
		return Active;
	}



	/**
	 * @param active the active to set
	 */
	public void setActive(BigInteger active) {
		Active = active;
	}



	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "CovidReport [id=" + id + ", State=" + State + ", Country=" + Country + ", lastUpdate=" + lastUpdate
				+ ", Confirmed=" + Confirmed + ", Deaths=" + Deaths + ", Recovered=" + Recovered + ", Active=" + Active
				+ ", lat=" + lat + ", long_=" + long_ + ", Combined_Key=" + Combined_Key + ", Incidence_Rate="
				+ Incidence_Rate + ", Case_Fatality_Ratio=" + Case_Fatality_Ratio + "]";
	}
	
	
	
	
	
}
