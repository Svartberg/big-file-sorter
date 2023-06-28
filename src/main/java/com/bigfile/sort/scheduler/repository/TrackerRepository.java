package com.bigfile.sort.scheduler.repository;

import com.bigfile.sort.scheduler.model.FileSortingJob;

public interface TrackerRepository {

    void save(FileSortingJob fileSortingJob);

    void update(FileSortingJob fileSortingJob);

    FileSortingJob findById(int id);

}
