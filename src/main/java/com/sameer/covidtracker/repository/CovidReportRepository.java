package com.sameer.covidtracker.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sameer.covidtracker.entity.CovidReport;
import com.sameer.covidtracker.entity.CovidReportCountryStates;

@Repository
public interface CovidReportRepository extends JpaRepository<CovidReport, Long> {

	@Query("from CovidReport as cr where cr.lastUpdate= :lastUpdate and cr.Country =:country and cr.State=:state and cr.Confirmed = :confirmed and  cr.Recovered =:recovered and cr.Deaths = :deaths ")
	Optional<CovidReport> ifRecordExists(@Param("lastUpdate") Date lastUpdate, @Param("country") String country,
			@Param("state") String state, @Param("confirmed") BigInteger confirmed,
			@Param("recovered") BigInteger recovered, @Param("deaths") BigInteger deaths);

	@Query(value = "SELECT * FROM covid_report where date(last_update) = :dateFrom LIMIT 1; ", nativeQuery = true)
	Optional<CovidReport> findOneByLastUpdate(@Param("dateFrom") String dateFrom);
	
	
	@Query("select new com.sameer.covidtracker.entity.CovidReportCountryStates(cr.Country,cr.State) from CovidReport as cr where cr.lastUpdate= ?1 ")
	List<CovidReportCountryStates> findCountryAndStateByLastDate(@Param("dateFrom") Date dateFrom);

	@Query("from CovidReport as cr where cr.lastUpdate= ?1 and CONCAT(cr.State, ' ' , cr.Country, ' ', cr.Combined_Key) LIKE %?2% ")
	Page<CovidReport> findByLastUpdate(Date lastUpdate, String keyword, Pageable pageable);

	@Query("from CovidReport as cr where cr.lastUpdate= ?1 and cr.State= ?2 and CONCAT(cr.State, ' ' , cr.Country, ' ', cr.Combined_Key) LIKE %?3% ")
	Page<CovidReport> findByLastUpdateAndState(Date lastUpdate, String state, String keyword, Pageable pageable);

	@Query("from CovidReport as cr where cr.lastUpdate= ?1 and cr.State= ?2 and cr.Country =?3 and CONCAT(cr.State, ' ' , cr.Country, ' ', cr.Combined_Key) LIKE %?4% ")
	Page<CovidReport> findByLastUpdateAndStateAndCountry(Date lastUpdate, String state, String country, String keyword,
			Pageable pageable);

	@Query("from CovidReport as cr where cr.lastUpdate= ?1 and cr.Country= ?2 and CONCAT(cr.State, ' ' , cr.Country, ' ', cr.Combined_Key) LIKE %?3% ")
	Page<CovidReport> findByLastUpdateAndCountry(Date date, String country, String keyword, Pageable paging);

}
