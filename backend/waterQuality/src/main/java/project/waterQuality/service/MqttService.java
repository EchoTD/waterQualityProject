package project.waterQuality.service;

import java.time.LocalDateTime;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import project.waterQuality.model.SensorData;
import project.waterQuality.repository.SensorRepository;

@Service
public class MqttService {
	@Value("${mqtt.broker-url}")
	private String brokerUrl;

	private MqttClient client;

	private final SensorRepository repository;

	public MqttService(SensorRepository repository) {
		this.repository = repository;
	}

	@PostConstruct
	public void init() throws MqttException {
		client = new MqttClient(brokerUrl, MqttClient.generateClientId());
		client.connect();
		client.subscribe("sensors/#", this::handleMessage);
	}

	private void handleMessage(String topic, MqttMessage message) {
		String payload = new String(message.getPayload());
		System.out.println("Received on topic " + topic + ": " + payload);
		try {
			ObjectMapper mapper = new ObjectMapper();
			SensorData sensorData = mapper.readValue(payload, SensorData.class);
			sensorData.setTimestamp(LocalDateTime.now());
			repository.save(sensorData);
		} catch (Exception e) {
			System.err.println("Error parsing or saving sensor data: " + e.getMessage());
		}
	}
}
