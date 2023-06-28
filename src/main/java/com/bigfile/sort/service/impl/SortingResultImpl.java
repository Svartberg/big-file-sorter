package com.bigfile.sort.service.impl;

import com.bigfile.sort.dto.SortResultDTO;
import com.bigfile.sort.exception.EntityNotFoundException;
import com.bigfile.sort.exception.EntityNotSortedException;
import com.bigfile.sort.mapper.TrackerEntityToSortResultMapper;
import com.bigfile.sort.scheduler.repository.TrackerRepository;
import com.bigfile.sort.service.SortingResult;
import org.springframework.stereotype.Service;

@Service
public class SortingResultImpl implements SortingResult {

    private final TrackerRepository trackerRepository;
    private final TrackerEntityToSortResultMapper mapper;

    public SortingResultImpl(TrackerRepository trackerRepository, TrackerEntityToSortResultMapper mapper) {
        this.trackerRepository = trackerRepository;
        this.mapper = mapper;
    }

    @Override
    public SortResultDTO getFilePathBySortId(int id) {
        var entity = trackerRepository.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity by id " + id + " not found");
        }
        if (entity.isSorted()) {
            return mapper.toSortResult(entity);
        } else {
            throw new EntityNotSortedException(entity.getErrorMessage());
        }
    }
}
