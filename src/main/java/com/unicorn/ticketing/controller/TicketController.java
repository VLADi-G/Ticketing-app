package com.unicorn.ticketing.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.ticketing.model.Ticket;
import com.unicorn.ticketing.model.Vehicle;
import com.unicorn.ticketing.repository.TicketRepository;
import com.unicorn.ticketing.repository.VehicleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "Operations related to issuing and validating tickets")
public class TicketController {

	private final TicketRepository ticketRepository;
	private final VehicleRepository vehicleRepository;

	public TicketController(TicketRepository ticketRepository, VehicleRepository vehicleRepository) {
		this.ticketRepository = ticketRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@GetMapping("/vehicle/{vehicleId}")
	@Operation(summary = "List tickets for a given vehicle")
	public List<Ticket> getTicketsByVehicle(@PathVariable Long vehicleId) {
		return ticketRepository.findByVehicleId(vehicleId);
	}

	@PostMapping
	@Operation(summary = "Issue a new ticket for a passenger and vehicle")
	public ResponseEntity<?> issueTicket(@RequestBody Ticket ticket) {
		if (ticket.getVehicle() == null || ticket.getVehicle().getId() == null) {
			return ResponseEntity.badRequest().body("Vehicle ID is required.");
		}

		Optional<Vehicle> vehicleOpt = vehicleRepository.findById(ticket.getVehicle().getId());
		if (!vehicleOpt.isPresent()) {
			return ResponseEntity.badRequest().body("Vehicle not found.");
		}

		ticket.setVehicle(vehicleOpt.get());
		Ticket saved = ticketRepository.save(ticket);
		return ResponseEntity.ok(saved);
	}

	@PatchMapping("/{ticketId}/validate")
	@Operation(summary = "Validate a ticket by ID")
	public ResponseEntity<?> validateTicket(@PathVariable Long ticketId) {
		Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
		if (!ticketOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Ticket ticket = ticketOpt.get();
		if (ticket.isValidated()) {
			return ResponseEntity.badRequest().body("Ticket has already been validated.");
		}

		ticket.setValidated(true);
		ticket.setValidatedAt(LocalDateTime.now());
		Ticket updated = ticketRepository.save(ticket);
		return ResponseEntity.ok(updated);
	}
}