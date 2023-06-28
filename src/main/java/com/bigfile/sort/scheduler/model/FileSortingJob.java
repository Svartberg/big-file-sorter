package com.bigfile.sort.scheduler.model;

import lombok.Data;

@Data
public class FileSortingJob {

    private int id;
    private String originalPath;
    private String resultPath;
    private boolean sorted;
    private String errorMessage;

    public FileSortingJob(String path) {
        this.id = -1;
        this.originalPath = path;
        this.sorted = false;
        this.errorMessage = "";
    }

    public FileSortingJob(FileSortingJob entity) {
        this.id = entity.getId();
        this.originalPath = entity.getOriginalPath();
        this.resultPath = entity.getResultPath();
        this.sorted = entity.isSorted();
        this.errorMessage = entity.getErrorMessage();
    }
}
