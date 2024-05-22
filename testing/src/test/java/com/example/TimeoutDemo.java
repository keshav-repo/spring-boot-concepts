package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class TimeoutDemo {

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void test() {
        try{
            Thread.sleep(1000);
        }catch (Exception e){

        }
    }
}
