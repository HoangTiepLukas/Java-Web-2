package com.javaweb2.controller;

import com.javaweb2.dto.CreateWorkerRequest;
import com.javaweb2.dto.WorkerDTO;
import com.javaweb2.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

	private final WorkerService workerService;

	public WorkerController(WorkerService workerService) {
		this.workerService = workerService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WorkerDTO createWorker(@Valid @RequestBody CreateWorkerRequest request) {
		return workerService.createWorker(request);
	}

	@GetMapping
	public List<WorkerDTO> listWorkers() {
		return workerService.listWorkers();
	}

	@GetMapping("/{id}")
	public WorkerDTO getWorker(@PathVariable Long id) {
		return workerService.getWorker(id);
	}
}
