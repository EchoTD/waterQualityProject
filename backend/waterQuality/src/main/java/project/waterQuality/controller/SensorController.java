package project.waterQuality.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.waterQuality.repository.SensorRepository;

@RestController
@RequestMapping("/api")
public class SensorController {

	private final SensorRepository repository;

	public SensorController(SensorRepository repository) {
		this.repository = repository;
	}
}
