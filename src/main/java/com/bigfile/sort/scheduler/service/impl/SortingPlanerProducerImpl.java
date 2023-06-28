package com.bigfile.sort.scheduler.service.impl;

import com.bigfile.sort.dto.SortResultDTO;
import com.bigfile.sort.mapper.TrackerEntityToSortResultMapper;
import com.bigfile.sort.scheduler.model.FileSortingJob;
import com.bigfile.sort.scheduler.repository.TrackerRepository;
import com.bigfile.sort.scheduler.service.SortingPlanerProducer;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class SortingPlanerProducerImpl implements SortingPlanerProducer {

    private final Queue<Integer> queue;
    private final TrackerRepository trackerRepository;
    private final TrackerEntityToSortResultMapper mapper;

    public SortingPlanerProducerImpl(Queue<Integer> queue,
                                     TrackerRepository trackerRepository,
                                     TrackerEntityToSortResultMapper mapper) {
        this.queue = queue;
        this.trackerRepository = trackerRepository;
        this.mapper = mapper;
    }

    @Override
    public SortResultDTO scheduleSorting(String path) {
        var data = new FileSortingJob(path);
        trackerRepository.save(data);
        queue.add(data.getId());
        return mapper.toSortResult(data);
    }
}
