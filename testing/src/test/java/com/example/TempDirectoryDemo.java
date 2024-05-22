package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TempDirectoryDemo {

    @TempDir
    private File anotherTempDir;

    @Test
    public void givenTestMethodWithTempDirectory_whenWriteToFile_thenContentIsCorrect(@TempDir(cleanup = CleanupMode.ON_SUCCESS) Path tempDir) throws IOException {
        Path numbers = tempDir.resolve("numbers.txt");

        List<String> lines = Arrays.asList("1", "2", "3");
        Files.write(numbers, lines);

        assertAll(() -> assertTrue(Files.exists(numbers)),
                () -> assertLinesMatch(lines, Files.readAllLines(numbers)));

        assertEquals(1,2);
    }

    @Test
    void givenFieldWithTempDirectoryFile_whenWriteToFile_thenContentIsCorrect() throws IOException {
        assertTrue(this.anotherTempDir.isDirectory());

        File letters = new File(anotherTempDir, "letters.txt");
        List<String> lines = Arrays.asList("x", "y", "z");

        Files.write(letters.toPath(), lines);

        assertAll(
                () -> assertTrue(Files.exists(letters.toPath())),
                () -> assertLinesMatch(lines, Files.readAllLines(letters.toPath())));
    }

}
