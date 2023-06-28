package com.bigfile.sort.sorter;

import com.bigfile.sort.exception.CustomIOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileMergerTest {

    private static final String RESOURCES_BIG_FILE = "src/test/resources/big_file";

    @Test
    void testMergeFiles() throws IOException {
        String filePath1 = createTempFile("file1.txt", "apple\nbanana1\n");
        String filePath2 = createTempFile("file2.txt", "cherry\n");
        String filePath3 = createTempFile("file3.txt", "banana2\norange\npear\n");

        List<String> filePaths = Arrays.asList(filePath1, filePath2, filePath3);

        FileMerger fileMerger = new FileMerger();
        String mergedFilePath = fileMerger.mergeFiles(RESOURCES_BIG_FILE, filePaths);

        List<String> mergedLines = Files.readAllLines(Path.of(mergedFilePath));

        List<String> expectedMergedLines = Arrays.asList("apple", "banana1", "banana2", "cherry",
                "orange", "pear");
        Assertions.assertEquals(expectedMergedLines, mergedLines);

        Files.deleteIfExists(Path.of(filePath1));
        Files.deleteIfExists(Path.of(filePath2));
        Files.deleteIfExists(Path.of(filePath3));
        Files.deleteIfExists(Path.of(mergedFilePath));
    }

    @Test
    void testMergeFiles_EmptyList() {
        List<String> filePaths = Collections.emptyList();

        FileMerger fileMerger = new FileMerger();
        String mergedFilePath = fileMerger.mergeFiles(RESOURCES_BIG_FILE, filePaths);

        Assertions.assertNull(mergedFilePath);
    }

    @Test
    void testMergeFiles_SingleFile() throws IOException {
        String filePath = createTempFile("file1.txt", "apple\nbanana\ncherry\n");

        List<String> filePaths = Collections.singletonList(filePath);

        FileMerger fileMerger = new FileMerger();
        String mergedFilePath = fileMerger.mergeFiles(RESOURCES_BIG_FILE, filePaths);

        List<String> mergedLines = Files.readAllLines(Path.of(mergedFilePath));

        List<String> expectedMergedLines = Arrays.asList("apple", "banana", "cherry");
        Assertions.assertEquals(expectedMergedLines, mergedLines);

        Files.deleteIfExists(Path.of(filePath));
        Files.deleteIfExists(Path.of(mergedFilePath));
    }

    @Test
    void testMergeFiles_FileNotFound() {
        List<String> filePaths = Collections.singletonList("nonexistent.txt");

        FileMerger fileMerger = new FileMerger();
        assertThrows(CustomIOException.class, () -> fileMerger.mergeFiles(RESOURCES_BIG_FILE, filePaths));
    }

    private String createTempFile(String fileName, String content) throws IOException {
        File tempFile = File.createTempFile(fileName, null);
        tempFile.deleteOnExit();
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(tempFile))) {
            writer.write(content);
        }
        return tempFile.getAbsolutePath();
    }
}