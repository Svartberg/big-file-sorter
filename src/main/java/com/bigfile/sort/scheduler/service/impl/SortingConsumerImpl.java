package com.bigfile.sort.scheduler.service.impl;

import com.bigfile.sort.exception.CustomIOException;
import com.bigfile.sort.scheduler.repository.TrackerRepository;
import com.bigfile.sort.scheduler.service.SortingConsumer;
import com.bigfile.sort.service.BigFileSorter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Queue;


@Component
public class SortingConsumerImpl implements SortingConsumer {

    private final Queue<Integer> queue;
    private final TrackerRepository trackerRepository;
    private final BigFileSorter bigFileSorter;

    public SortingConsumerImpl(Queue<Integer> queue,
                               TrackerRepository trackerRepository, BigFileSorter bigFileSorter) {
        this.queue = queue;
        this.trackerRepository = trackerRepository;
        this.bigFileSorter = bigFileSorter;
    }

    @Override
    @Scheduled(fixedDelay = 1000)
    public void consume() {
        if (!queue.isEmpty()) {
            var id = queue.poll();
            var entity = trackerRepository.findById(id);

            try {
                String resPath = bigFileSorter.sortBigFile(entity.getOriginalPath());
                entity.setResultPath(resPath);
                entity.setSorted(true);
            } catch (CustomIOException | IOException e) {
                entity.setErrorMessage(e.getMessage());
            }
            trackerRepository.update(entity);
        }
    }
}
