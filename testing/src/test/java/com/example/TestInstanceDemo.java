package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
public class TestInstanceDemo {

    @BeforeAll
    public static void setup(){
    }

    @Test
    public void test2(){
    }

    @Test
    public void test(){

    }
}
