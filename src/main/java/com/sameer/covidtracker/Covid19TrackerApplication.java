package com.sameer.covidtracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sameer.covidtracker.entity.CovidReport;
import com.sameer.covidtracker.repository.CovidReportRepository;

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class Covid19TrackerApplication {
	
	public static String Static_Data_URL = "https://github.com/CSSEGISandData/COVID-19/raw/master/csse_covid_19_data/csse_covid_19_daily_reports/";
	
	
	@Autowired
	CovidReportRepository covidReportRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerApplication.class, args);

	}
	
	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
        System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
    }
	
	//@PostConstruct
    @Scheduled(cron = "0 0 3 * * *", zone = "UTC")
    public void ScheduledFetchCoronaVirusData(){
		// Create new Date 22-01-2020
				@SuppressWarnings("deprecation")
				Date dateFrom = new Date("01/22/2020");
//				Date dateFrom = new Date("11/05/2020"); 
				System.out.println("dateFrom:" + dateFrom);
				
				Date dateTo = new Date();
				System.out.println("dateto:" + dateTo);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				while(dateFrom.compareTo(dateTo) < 0) {
					// dateFrom occurs before dateTo
					System.out.println("Fetching Data for " + dateFrom);
					
					Optional<CovidReport> option = covidReportRepository.findOneByLastUpdate(sdf.format(dateFrom));
					
					if(option.isPresent()) {
						System.out.println("Record Already Fetched by the scheduler");
					} else {
						System.out.println("Record not found");
						GetFetchedCoronaVirusData(dateFrom);
					}
					
					// add the day
					dateFrom = new Date(dateFrom.getTime() + TimeUnit.DAYS.toMillis(1));
				}	
		         
    }


	private void GetFetchedCoronaVirusData(Date dateFrom) {

		// prepare Data url for Covid Cases 
        String Date_URL = new SimpleDateFormat("MM-dd-yyyy").format(dateFrom) + ".csv";
        String CSV_DATA_URL = new String(Covid19TrackerApplication.Static_Data_URL + Date_URL);
        System.out.println(CSV_DATA_URL);
        
        @SuppressWarnings("deprecation")
		Date newFormatDataDate = new Date("03/22/2020");
        
        try {

	        HttpClient client = HttpClientBuilder.create().build();
	        HttpGet request = new HttpGet(CSV_DATA_URL);
	        HttpResponse response = client.execute(request);
	        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        String resp = "",line;
	        while ((line = rd.readLine()) != null) {    
	        	resp += line + "\n";
	        }
	        System.out.println("Response Code : " + response.getStatusLine());

	        if ( response.getStatusLine().getStatusCode() == 200 ) {
		        StringReader csvBodyReader = new StringReader(resp);
				Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvBodyReader);
				
				// Create new List of covid 19 reports
				List<CovidReport> covidReports = new ArrayList<CovidReport>();
				System.out.println("Creating covid-19 records List");
				for (CSVRecord record : records) {
					try {
			            CovidReport covidReport = new CovidReport();
			            try {
			            	if (dateFrom.compareTo(newFormatDataDate) == -1 ) {
			            		covidReport.setState(record.get(0));
			            	} else {
//			            	covidReport.setState(record.get("﻿Province/State"));
			            		covidReport.setState(record.get("Province_State"));
			            	}
			            } catch(IllegalArgumentException e) {
//			            	covidReport.setState(record.get("Province_State"));
			            	e.printStackTrace();
			            }
			            
			            try {
			            	if (dateFrom.compareTo(newFormatDataDate) == -1) {
			            		covidReport.setCountry(record.get(1));
			            	} else {
//			            	covidReport.setCountry(record.get("Country/Region"));
			            	covidReport.setCountry(record.get("Country_Region"));
			            	}
			            } catch(IllegalArgumentException e) {
			            	e.printStackTrace();
			            }
			            
		
						
						
	//					String lastUpdate = new String();
	//					try {
	//						lastUpdate = record.get("Last Update");
	//					} catch (IllegalArgumentException e) {
	//						lastUpdate = record.get("Last_Update");
	//					}
	//					
	//					
	//					
	//					@SuppressWarnings("deprecation")
	//					Date last_update ; //= new Date(lastUpdate);
	//					try {
	//						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	//			            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	//						last_update = sdf.parse(lastUpdate);
	//					} catch (ParseException e) {
	//						// initiate the date
	//						last_update = dateFrom;
	//					}
			            covidReport.setLastUpdate(dateFrom);
			            
			            String confirmed = record.get("Confirmed");
			            
			            if(confirmed==null || confirmed.isEmpty()) {
			            	confirmed = "0";
			            }
			            
			            covidReport.setConfirmed(new BigInteger(confirmed));
			            
			            String Deaths = record.get("Deaths");
			            
			            if(Deaths==null || Deaths.isEmpty()) {
			            	Deaths = "0";
			            }	
			            covidReport.setDeaths(new BigInteger(Deaths));
			            
			            String Recovered = record.get("Recovered");
			            
			            if(Recovered==null || Recovered.isEmpty()) {
			            	Recovered = "0";
			            }
			            covidReport.setRecovered(new BigInteger(Recovered));
			            
			            try {
				            String Active = record.get("Active");
				            
				            if(Active==null || Active.isEmpty()) {
				            	Active = "0";
				            }
				            covidReport.setActive(new BigInteger(Active));
			            } catch(Exception e) {}
			            
			            
			            try {
							covidReport.setLat(record.get("Lat"));
						} catch(Exception e) {}
			            
			            try {
							covidReport.setLat(record.get("Latitude"));
						} catch(Exception e) {}
			            
			            
						
						
						try {
							covidReport.setLong_(record.get("Long_"));
						} catch(Exception e) {}
						
						try {
							covidReport.setLong_(record.get("Longitude"));
						} catch(Exception e) {}
						
						
						
						try {
							covidReport.setCombined_Key(record.get("Combined_Key"));
						} catch(Exception e) {}
						
						try {
							String Incidence_Rate = record.get("Incidence_Rate");
							if(Incidence_Rate==null || Incidence_Rate.isEmpty()) {
								Incidence_Rate = "0";
				            }	
							covidReport.setIncidence_Rate(new Double(Incidence_Rate));
						} catch(Exception e) {}
						
						
						try {
							String case_facility_ratio = record.get("Case-Fatality_Ratio");
							if(case_facility_ratio==null || case_facility_ratio.isEmpty()) {
								case_facility_ratio = "0";
				            }	
							covidReport.setCase_Fatality_Ratio(new Double(case_facility_ratio));
						} catch(Exception e) {}
			            
						covidReports.add(covidReport);
//			            System.out.println("Records added : " + covidReports.size());
//			            Optional<CovidReport> option = covidReportRepository.ifRecordExists(covidReport.getLastUpdate(), covidReport.getCountry(), 
//			            		covidReport.getState(), covidReport.getConfirmed(), covidReport.getRecovered(), covidReport.getDeaths());
//			            if(!option.isPresent()) {
//			            	covidReportRepository.save(covidReport);
//			            }
					} catch(Exception e) {
						e.printStackTrace();
					}
		            
		            
		        }
				System.out.println("Covid records for " + dateFrom + " are : " + covidReports.size());
				// save all covid Reports to the DB
				if(!covidReports.isEmpty()) {
					long start = System.currentTimeMillis();
					covidReportRepository.saveAll(covidReports);
					long finish =System.currentTimeMillis();
					
					System.out.println("Stored "  + covidReports.size() + " records to DB and takes elapsed time : " 
										+ (finish - start) +".");
				}
				
	        }
			
			
        } catch(IOException e) {
        	e.printStackTrace();
        }
		
	}

}
