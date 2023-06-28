package com.bigfile.sort.sorter;

import com.bigfile.sort.exception.CustomIOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileSplitterTest {

    @Mock
    ChunkWriter chunkWriter;
    @InjectMocks
    FileSplitter fileSplitter;

    @Test
    void readAndSortFiles_success() throws IOException {
        var filePath = "src/test/resources/big_file";
        when(chunkWriter.writeFile(anyList(), anyString())).thenReturn("1").thenReturn("2")
                .thenReturn("3");
        List<String> stringList = fileSplitter.readAndSortFiles(filePath, 10);

        assertEquals(3, stringList.size());
    }

    @Test
    void readAndSortFiles_smallFile() throws IOException {
        var filePath = "src/test/resources/big_file";
        when(chunkWriter.writeFile(anyList(), anyString())).thenReturn("1");
        List<String> stringList = fileSplitter.readAndSortFiles(filePath, 1000);

        assertEquals(1, stringList.size());
    }

    @Test
    void readAndSortFiles_noFile() {
        var filePath = "src/test/resources/nonExisting";
        assertThrows(CustomIOException.class, () -> fileSplitter.readAndSortFiles(filePath, 10));
    }
}