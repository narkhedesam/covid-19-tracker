package com.sameer.covidtracker.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sameer.covidtracker.entity.CovidReport;
import com.sameer.covidtracker.entity.CovidReportCountryStates;
import com.sameer.covidtracker.repository.CovidReportRepository;


@Service
public class CovidReportService {

	
	
	@Autowired
	CovidReportRepository CovidReportRepository;
	
	public List<CovidReportCountryStates> findCountryAndStateByLastDate(Date date){
		return CovidReportRepository.findCountryAndStateByLastDate(date);
	}
	
	public Page<CovidReport> findByLastUpdateAndStateAndCountry(Date lastUpdate, String state, String country, String keyword,
			Pageable pageable){
		return CovidReportRepository.findByLastUpdateAndStateAndCountry(lastUpdate, state, country, keyword, pageable);
	}
	
	
	public Page<CovidReport> findByLastUpdateAndState(Date lastUpdate, String state, String keyword, Pageable pageable){
		return CovidReportRepository.findByLastUpdateAndState(lastUpdate, state, keyword, pageable);
	}
	
	public Page<CovidReport> findByLastUpdateAndCountry(Date lastUpdate, String country, String keyword, Pageable pageable){
		return CovidReportRepository.findByLastUpdateAndCountry(lastUpdate, country, keyword, pageable);
	}
	
	public Page<CovidReport> findByLastUpdate(Date lastUpdate, String keyword, Pageable pageable){
		return CovidReportRepository.findByLastUpdate(lastUpdate, keyword, pageable);
	}
	
	
	
}
