package com.example;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @TestTemplate
    @ExtendWith(CalculatorTestTemplateProvider.class)
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }
}
