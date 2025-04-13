package com.example.dreamshop.data;

import com.example.dreamshop.model.User;
import com.example.dreamshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUsersIfNotExists();
    }

    private void createDefaultUsersIfNotExists() {
        for (int i = 0; i <5; i++){
            String defaultEmail = "user" + i + "@example.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
                User user = new User();
                user.setFirstname("User" + i);
                user.setLastname("LastName" + i);
                user.setEmail(defaultEmail);
                user.setPassword("123456");
                userRepository.save(user);
                System.out.println("Created default user: " + defaultEmail);
        }
    }
}
