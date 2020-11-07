package com.sameer.covidtracker.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sameer.covidtracker.entity.CovidReport;
import com.sameer.covidtracker.entity.CovidReportCountryStates;
import com.sameer.covidtracker.service.CovidReportService;

@Controller
public class WebController {
	
	@Autowired
	CovidReportService covidReportService;
	
	
	
	// Handle Home Request and redirect to index
	@RequestMapping(value = "/")
	public void home(HttpServletResponse response) throws IOException {
		response.sendRedirect("/index");
	}
	
	
	@RequestMapping(value = "/index")
	public String index(Model model,
			@RequestParam(name = "date", defaultValue = "2020-01-22", required = false) String strDate,
			@RequestParam(name = "state", defaultValue = "None",required = false) String state,
			@RequestParam(name = "country", defaultValue = "None", required = false) String country,
			@RequestParam(name = "entries", defaultValue = "10", required = false) int entries,
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "search", defaultValue = "", required = false) String keyword
			) throws ParseException {
		
		// Create SDF for to format date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// Parse date from string
		Date date = sdf.parse(strDate);
		
		// init state attr
		if("None".equals(state)) {
			state = new String();
		}
		
		// init country attr
		if("None".equals(country)) {
			country = new String();
		}
		
		
		// Create pageable from PageRequest with page and max-record per page
		Pageable paging = PageRequest.of(page, entries);
		
		// Create Country and States list from the date 			
		List<CovidReportCountryStates> CountryStateList = covidReportService.findCountryAndStateByLastDate(date);
		
		List<String> countries = new ArrayList<>();
		List<String> states = new ArrayList<>();
		
		for(CovidReportCountryStates CountryStateCombo : CountryStateList) {
			if(!CountryStateCombo.getCountry().isEmpty()) {
				if(!countries.contains(CountryStateCombo.getCountry())) {
					countries.add(CountryStateCombo.getCountry());
				}
			}
	
			if(!CountryStateCombo.getState().isEmpty()) {
				if(!states.contains(CountryStateCombo.getState())) {
					states.add(CountryStateCombo.getState());
				}
				
			}
			
		}
		
		// get Paging Data from DB.
		Page<CovidReport> covidReportPage;
		
		List<CovidReport> covidReports = new ArrayList<>();
		if (!state.isEmpty() && !country.isEmpty()) {
			covidReportPage = covidReportService.findByLastUpdateAndStateAndCountry(date, state, country, keyword, paging);
			
		}else if (!state.isEmpty() && country.isEmpty()) {
			covidReportPage = covidReportService.findByLastUpdateAndState(date, state, keyword, paging);

		}else if (state.isEmpty() && !country.isEmpty()) {
			covidReportPage = covidReportService.findByLastUpdateAndCountry(date, country, keyword, paging);
			
		}else {
			covidReportPage = covidReportService.findByLastUpdate(date, keyword, paging);
			 
		}
		
		// Get Actual contents
		covidReports = covidReportPage.getContent();
		
		int totalPages = covidReportPage.getTotalPages();
		System.out.println("totalPages:"+totalPages);
		
		int number = covidReportPage.getNumber();
		System.out.println("number:"+number);
		
		int numberOfElements = covidReportPage.getNumberOfElements();
		System.out.println("numberOfElements:"+numberOfElements);
		
		int pageSize = covidReportPage.getSize();
		System.out.println("pageSize:"+pageSize);
		
		int totalElements = (int) covidReportPage.getTotalElements();
		System.out.println("totalElements:"+totalElements);
		
		boolean firstPage = covidReportPage.isFirst();
		boolean lastPage = covidReportPage.isLast();
		
		
		
		
		
		model.addAttribute("reportDate", strDate);
		
		model.addAttribute("countries", countries);
		
		model.addAttribute("states", states);
		
		model.addAttribute("selectedState", state);
		
		model.addAttribute("selectedCountry", country);
		
		model.addAttribute("covidReports", covidReports);
		
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("entries", entries);
		
		model.addAttribute("totalPages", totalPages);
		
		model.addAttribute("number", number);

		model.addAttribute("numberOfElements", numberOfElements);
		
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("totalElements", totalElements);
		
		model.addAttribute("firstPage", firstPage);
		
		model.addAttribute("lastPage", lastPage);
		
		
		return "index";
	}

}
