package com.bigfile.sort.scheduler.service;

import com.bigfile.sort.dto.SortResultDTO;

public interface SortingPlanerProducer {

    SortResultDTO scheduleSorting(String path);
}
