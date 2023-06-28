package com.bigfile.sort.sorter;

import com.bigfile.sort.exception.CustomIOException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FileSplitter {

    private final ChunkWriter writer;

    public FileSplitter(ChunkWriter writer) {
        this.writer = writer;
    }

    public List<String> readAndSortFiles(String fileName, long chunkSize)
            throws IOException {
        List<String> filesPaths = new ArrayList<>();

        fileIsExist(fileName);

        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                new FileInputStream(fileName))))) {
            String line;
            int currChunkSize = 0;
            int iter = 0;

            while ((line = br.readLine()) != null) {
                lines.add(line);
                currChunkSize += line.length() + 1;
                if (currChunkSize >= chunkSize) {
                    currChunkSize = 0;
                    lines.sort(Comparator.naturalOrder());
                    filesPaths.add(writer.writeFile(lines, String.valueOf(iter++)));
                    lines.clear();
                }
            }

            lines.sort(Comparator.naturalOrder());
            filesPaths.add(writer.writeFile(lines, String.valueOf(iter)));
            lines.clear();
        }
        return filesPaths;
    }

    private void fileIsExist(String fileName) {
        File file = new File(fileName);
        if (!file.exists())
            throw new CustomIOException("File is no exist in path: " + fileName);
    }
}
