package com.example.marketplace;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/marketplace")
@RequiredArgsConstructor
public class DemoController {
    private final Faker faker;
    private final UserRepository userRepository;

    @GetMapping("/fromDb")
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @GetMapping
    public List<Character> all() {
       return Stream.generate(() -> new Character(getName(), getLocation(), getPets()))
               .limit(20)
               .toList();

    }

    private List<String> getPets() {
        return Stream.generate(() -> faker.animal().name())
                .limit(ThreadLocalRandom.current().nextInt(5) + 1)
                .toList();
    }

    private String getLocation() {
        return faker.lordOfTheRings().location();
    }

    private String getName() {
        return faker.lordOfTheRings().character();
    }

}
