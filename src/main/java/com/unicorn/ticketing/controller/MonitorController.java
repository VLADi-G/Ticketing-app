package com.unicorn.ticketing.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.ticketing.repository.TicketRepository;
import com.unicorn.ticketing.repository.VehicleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/monitor")
@Tag(name = "Monitoring", description = "System health, metrics, and logs")
public class MonitorController {

	private final TicketRepository ticketRepository;
	private final VehicleRepository vehicleRepository;

	public MonitorController(TicketRepository ticketRepository, VehicleRepository vehicleRepository) {
		this.ticketRepository = ticketRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@GetMapping("/health")
	@Operation(summary = "Check application and database health")
	public ResponseEntity<String> healthCheck() {
		try {
			// Try to query something lightweight to verify DB connection
			vehicleRepository.count();
			return ResponseEntity.ok("OK");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("NOT OK: " + e.getMessage());
		}
	}

	@GetMapping("/metrics")
	@Operation(summary = "Get system metrics")
	public Map<String, Object> metrics() {
		Map<String, Object> stats = new HashMap<>();
		long totalTickets = ticketRepository.count();
		long validatedTickets = ticketRepository.findAll().stream().filter(t -> t.isValidated()).count();
		long vehicleCount = vehicleRepository.count();

		stats.put("totalTickets", totalTickets);
		stats.put("validatedTickets", validatedTickets);
		stats.put("registeredVehicles", vehicleCount);

		return stats;
	}

	@GetMapping("/logs")
	@Operation(summary = "Fetch recent log lines with pagination")
	public ResponseEntity<?> logs(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size) {
		Path logFile = Paths.get("logs/application.log");

		if (!Files.exists(logFile)) {
			return ResponseEntity.status(404).body("Log file not found.");
		}

		try {
			List<String> allLines = Files.readAllLines(logFile);
			int totalLines = allLines.size();

			int fromIndex = Math.max(0, totalLines - (page + 1) * size);
			int toIndex = Math.min(totalLines, totalLines - page * size);

			List<String> pagedLines = allLines.subList(fromIndex, toIndex);

			return ResponseEntity.ok(pagedLines);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Error reading log file: " + e.getMessage());
		}
	}
}
