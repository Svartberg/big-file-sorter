package com.bigfile.sort.scheduler.repository.impl;

import com.bigfile.sort.exception.EntityNotFoundException;
import com.bigfile.sort.scheduler.model.FileSortingJob;
import com.bigfile.sort.scheduler.repository.TrackerRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryTrackerRepositoryImpl implements TrackerRepository {
    private final Map<Integer, FileSortingJob> memory = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    @Override
    public void save(FileSortingJob fileSortingJob) {
        fileSortingJob.setId(id.incrementAndGet());
        memory.put(fileSortingJob.getId(), fileSortingJob);
    }

    @Override
    public synchronized void update(FileSortingJob fileSortingJob) {
        if (memory.containsKey(fileSortingJob.getId())) {
            memory.replace(fileSortingJob.getId(), fileSortingJob);
        } else {
            throw new EntityNotFoundException("Entity by id " + fileSortingJob.getId() + " not found");
        }
    }

    @Override
    public FileSortingJob findById(int id) {
        if (memory.containsKey(id)) {
            return new FileSortingJob(memory.get(id));
        } else {
            throw new EntityNotFoundException("Entity by id " + id + " not found");
        }
    }
}
