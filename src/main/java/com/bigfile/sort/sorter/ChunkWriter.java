package com.bigfile.sort.sorter;

import com.bigfile.sort.configuration.BigFileSorterConfig;
import com.bigfile.sort.exception.CustomIOException;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ChunkWriter {

    private final BigFileSorterConfig bigFileSorterConfig;

    public ChunkWriter(BigFileSorterConfig bigFileSorterConfig) {
        this.bigFileSorterConfig = bigFileSorterConfig;
    }

    public static Path createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();

        try {
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            return path;

        } catch (IOException e) {
            throw new CustomIOException("Failed to create file to path: " + parentDir);
        }
    }

    public String writeFile(List<String> list, String appendix) {

        String filePath = bigFileSorterConfig.getSplitFilePath() + "/tmp_" + appendix + ".txt";
        createFileIfNotExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            list.forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    throw new CustomIOException("Failed to write newLine in file to path: " + filePath);
                }
            });
            return filePath;

        } catch (IOException e) {
            throw new CustomIOException("Failed to write file to path: " + filePath);
        }
    }
}
