package project.waterQuality.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.waterQuality.model.SensorData;

public interface SensorRepository extends JpaRepository<SensorData, Long> {

}
