package project.waterQuality;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGen {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java HashGen <raw-password>");
            System.exit(1);
        }

        String rawPassword = args[0];
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode(rawPassword);

        System.out.println("Raw:    " + rawPassword);
        System.out.println("BCrypt: " + hashed);
    }
}
