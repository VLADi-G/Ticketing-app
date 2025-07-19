package com.unicorn.ticketing.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.ticketing.model.Vehicle;
import com.unicorn.ticketing.repository.VehicleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "Operations for registering and listing vehicles")
public class VehicleController {

	private final VehicleRepository vehicleRepository;

	public VehicleController(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	@PostMapping
	@Operation(summary = "Register a new vehicle")
	public ResponseEntity<Vehicle> registerVehicle(@RequestBody Vehicle vehicle) {
		if (vehicleRepository.existsByRegistrationNumber(vehicle.getRegistrationNumber())) {
			return ResponseEntity.badRequest().build();
		}
		Vehicle saved = vehicleRepository.save(vehicle);
		return ResponseEntity.ok(saved);
	}

	@GetMapping
	@Operation(summary = "List all registered vehicles")
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}
}