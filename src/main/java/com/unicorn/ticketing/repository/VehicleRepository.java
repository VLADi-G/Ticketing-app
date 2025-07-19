package com.unicorn.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicorn.ticketing.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	boolean existsByRegistrationNumber(String registrationNumber);
}
