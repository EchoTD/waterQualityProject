package project.waterQuality;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.waterQuality.model.User;
import project.waterQuality.repository.UserRepository;

@SpringBootApplication
public class WaterQualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterQualityApplication.class, args);
	}

	@Bean
	public CommandLineRunner createTestUser(UserRepository userRepo,
			PasswordEncoder passwordEncoder) {
		return args -> {
			String username = "testuser1";
			String rawPassword = "testpass";
			if (userRepo.findByUsername(username) == null) {
				User u = new User();
				u.setUsername(username);
				// BCrypt the test password
				u.setPassword(passwordEncoder.encode(rawPassword));
				userRepo.save(u);
				System.out.println("✔️  Created user '" + username + "' with password '" + rawPassword
						+ "'");
			} else {
				System.out.println("ℹ️  User '" + username + "' already exists, skipping creation");
			}
		};
	}
}
