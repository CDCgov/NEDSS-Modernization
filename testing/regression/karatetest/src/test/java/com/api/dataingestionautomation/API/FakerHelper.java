package com.api.dataingestionautomation.API;

import com.github.javafaker.Faker;

public class FakerHelper {
    private static final Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }
}
