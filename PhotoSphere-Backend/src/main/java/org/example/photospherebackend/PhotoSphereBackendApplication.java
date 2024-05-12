package org.example.photospherebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhotoSphereBackendApplication {

    public static void main(String[] args) {
//         TODO Addresses Table Handling
//         Basic
//         The following line starts the Spring Boot application with default configurations.
//         Uncomment it to run the application without any specific profile-based customization.
//         SpringApplication.run(PhotoSphereBackendApplication.class, args);
//
//         Method 1: Using setAdditionalProfiles
//         Activate the "local" profile using setAdditionalProfiles
            SpringApplication app = new SpringApplication(PhotoSphereBackendApplication.class);
            app.setAdditionalProfiles("local");
            app.run(args);
//
//         Method 2: Using setDefaultProperties
//         Uncomment the following line to set "local" as the default profile
//         SpringApplication.setDefaultProperties(Collections.singletonMap("spring.profiles.default", "local"));
//         SpringApplication.run(PhotoSphereBackendApplication.class, args);
//
//         Method 3: Using setActiveProfiles on the application context
//         Uncomment the following lines to set "local" as an active profile on the application context
//         ConfigurableApplicationContext context = SpringApplication.run(PhotoSphereBackendApplication.class, args);
//         context.getEnvironment().setActiveProfiles("local");
    }

}
