package com.bigfile.sort.service.impl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class BigFileSorterImplTest {

    @Autowired
    BigFileSorterImpl bigFileSorter;

    @Test
    void sortBigFile() throws IOException {
        var filePath = "src/test/resources/big_file";
        var sortedFile = "src/test/resources/big_file_presorted";
        String resFile = bigFileSorter.sortBigFile(filePath);
        Path resPath = Paths.get(resFile);

        assertTrue(resPath.toFile().exists());
        assertTrue(compareFiles(resPath, Paths.get(sortedFile)));

        Files.deleteIfExists(resPath);
    }

    @Test
    void sortBigFile_chunks() throws IOException {
        var filePath = "src/test/resources/big_file";
        var sortedFile = "src/test/resources/big_file_presorted";
        String resFile = bigFileSorter.sortBigFile(filePath, 10);
        Path resPath = Paths.get(resFile);

        assertTrue(resPath.toFile().exists());
        assertTrue(compareFiles(resPath, Paths.get(sortedFile)));

        Files.deleteIfExists(resPath);
    }


    private boolean compareFiles(Path file1, Path file2) throws IOException {
        byte[] file1Bytes = Files.readAllBytes(file1);
        byte[] file2Bytes = Files.readAllBytes(file2);

        assertEquals(file1Bytes.length, file2Bytes.length);

        for (int i = 0; i < file1Bytes.length; i++) {
            if (file1Bytes[i] != file2Bytes[i]) {
                return false;
            }
        }

        return true;
    }
}