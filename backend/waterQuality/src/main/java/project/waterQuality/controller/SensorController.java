package project.waterQuality.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.waterQuality.model.SensorData;
import project.waterQuality.repository.SensorRepository;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SensorController {

	private final SensorRepository repository;

	public SensorController(SensorRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/sensor-data")
	public ResponseEntity<List<SensorData>> getAllSensorData() {
		List<SensorData> data = repository.findAll();
		return ResponseEntity.ok(data);
	}

	@GetMapping("/sensor-data/{id}")
	public ResponseEntity<SensorData> getSensorDataById(@PathVariable Long id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
