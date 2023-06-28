package com.bigfile.sort.sorter;

import com.bigfile.sort.exception.CustomIOException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class FileMerger {

    public String mergeFiles(String originalFilePath, List<String> filePaths) {
        Path path = Paths.get(originalFilePath);
        String tempFileName = "sorted_" + path.getFileName().toString();
        path = path.resolveSibling(tempFileName);

        if (filePaths == null || filePaths.isEmpty()) {
            return null;
        }

        TreeMap<String, BufferedReader> tree = new TreeMap<>();
        List<BufferedReader> readers = new ArrayList<>();
        BufferedWriter writer = null;

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            File tempFile = path.toFile();
            writer = new BufferedWriter(new FileWriter(tempFile));

            for (String filePath : filePaths) {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                readers.add(reader);
                String line = reader.readLine();
                if (line != null) {
                    tree.put(line, reader);
                }
            }

            while (!tree.isEmpty()) {
                Map.Entry<String, BufferedReader> entry = tree.pollFirstEntry();
                writer.write(entry.getKey());
                writer.newLine();
                String nextLine = entry.getValue().readLine();
                if (nextLine != null) {
                    tree.put(nextLine, entry.getValue());
                }
            }

            return tempFile.getPath();
        } catch (FileNotFoundException e) {
            throw new CustomIOException("File not found: " + e.getMessage());
        } catch (IOException e) {
            throw new CustomIOException("Failed to merge files: " + e.getMessage());

        } finally {
            closeResources(writer, readers);
            deleteTempFiles(filePaths);
        }
    }

    private void deleteTempFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            try {
                Files.deleteIfExists(Path.of(filePath));
            } catch (IOException e) {
                throw new CustomIOException("Failed to delete temp file to path: " + filePath);
            }
        }
    }

    private void closeResources(BufferedWriter writer, List<BufferedReader> readers) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new CustomIOException("Failed close writer session");
        }

        for (BufferedReader reader : readers) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new CustomIOException("Failed close reader session");
            }
        }
    }

}
