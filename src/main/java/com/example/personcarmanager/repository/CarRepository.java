package com.example.personcarmanager.repository;

import com.example.personcarmanager.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByOwnerId(Long personId);

    @Query("SELECT COUNT(DISTINCT c.model) FROM Car c")
    Long countDistinctModel();
}
