package com.example.marketplace;

import java.util.List;

public record Character(String name,
                        String location,
                        List<String> pets) {
}
