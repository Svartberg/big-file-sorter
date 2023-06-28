package com.bigfile.sort.service;

import com.bigfile.sort.dto.SortResultDTO;

public interface SortingResult {

    SortResultDTO getFilePathBySortId(int id);
}
