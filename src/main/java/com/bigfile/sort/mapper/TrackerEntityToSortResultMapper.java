package com.bigfile.sort.mapper;

import com.bigfile.sort.dto.SortResultDTO;
import com.bigfile.sort.scheduler.model.FileSortingJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackerEntityToSortResultMapper {

    @Mapping(target = "ready", source = "sorted")
    SortResultDTO toSortResult(FileSortingJob entity);
}
