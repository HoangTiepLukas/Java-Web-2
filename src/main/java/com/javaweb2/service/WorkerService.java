package com.javaweb2.service;

import com.javaweb2.dto.CreateWorkerRequest;
import com.javaweb2.dto.WorkerDTO;
import com.javaweb2.entity.Worker;
import com.javaweb2.repository.WorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkerService {

	private final WorkerRepository workerRepository;

	public WorkerService(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}

	public WorkerDTO createWorker(CreateWorkerRequest request) {

		Worker worker = new Worker();
		worker.setName(request.getName());
		worker.setEmail(request.getEmail());
		worker.setPassword(request.getPassword());
		worker.setRole(request.getRole());
		worker.setCreatedAt(LocalDateTime.now());

		Worker createdWorker = workerRepository.save(worker);

		return mapToDTO(createdWorker);
	}

	public List<WorkerDTO> listWorkers() {
		return workerRepository.findAll().stream().map(this::mapToDTO).toList();
	}

	public WorkerDTO getWorker(Long id) {
		Worker worker = workerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
		return mapToDTO(worker);
	}

	private WorkerDTO mapToDTO(Worker worker) {
		WorkerDTO workerDTO = new WorkerDTO();
		workerDTO.id = worker.getId();
		if (worker.getSupplier() != null) {
			workerDTO.supplierId = worker.getSupplier().getId();
		}
		workerDTO.name = worker.getName();
		workerDTO.email = worker.getEmail();
		workerDTO.role = worker.getRole();
		workerDTO.createdAt = worker.getCreatedAt();
		return workerDTO;
	}
}
