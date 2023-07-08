package com.example.marketplace;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/marketplace")
public class DemoController {

    @GetMapping
    public List<Customer> all() {
        return List.of(new Customer(1L, "Petro", "Poroshenko", "poroh@mail.com"),
                new Customer(2L, "Volodymyr", "Zelensky", "zelya@mail.com"),
                new Customer(3L, "Leonid", "Kuchma", "kuchma@mail.ua"));
    }
}
