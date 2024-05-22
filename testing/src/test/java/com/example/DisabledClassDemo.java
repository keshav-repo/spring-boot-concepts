package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Disabled until bug #99 has been fixed")
class DisabledClassDemo {
    @Test
    void testWillBeSkipped() {
        Assertions.assertEquals(2,3);
    }

    @Test
    @Disabled("Disabled until bug #42 has been resolved")
    void testWillBeSkipped2() {
        Assertions.assertEquals(2,3);
    }
}