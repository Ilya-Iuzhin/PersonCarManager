package com.example.personcarmanager.repository;

import com.example.personcarmanager.dto.request.StatisticsDTO;
import com.example.personcarmanager.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT (SELECT COUNT(*) FROM person) AS personCount, " +
            "(SELECT COUNT(*) FROM car) AS carCount, " +
            "(SELECT COUNT(DISTINCT model) FROM car) AS uniqueVendorCount",
            nativeQuery = true)
    StatisticsDTO getStatistics();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM person; DELETE FROM car;", nativeQuery = true)
    void deleteAllData();


}
