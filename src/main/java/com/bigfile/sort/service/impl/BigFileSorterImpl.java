package com.bigfile.sort.service.impl;

import com.bigfile.sort.service.BigFileSorter;
import com.bigfile.sort.sorter.FileMerger;

import com.bigfile.sort.sorter.FileSplitter;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BigFileSorterImpl implements BigFileSorter {

    private final FileSplitter splitter;
    private final FileMerger merger;

    public BigFileSorterImpl(FileSplitter splitter, FileMerger merger) {
        this.splitter = splitter;
        this.merger = merger;
    }

    @Override
    public String sortBigFile(String path) throws IOException {
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        return sortBigFile(path, heapFreeSize / 2);
    }

    @Override
    public String sortBigFile(String path, long chunkSize) throws IOException {
        List<String> files = splitter.readAndSortFiles(path, chunkSize);
        return merger.mergeFiles(path, files);
    }
}
