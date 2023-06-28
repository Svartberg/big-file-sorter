package com.bigfile.sort.controller;

import com.bigfile.sort.dto.SortResultDTO;
import com.bigfile.sort.dto.SorterRequestDTO;
import com.bigfile.sort.exception.RequestValidationException;
import com.bigfile.sort.scheduler.service.SortingPlanerProducer;
import com.bigfile.sort.service.SortingResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sorter")
@Api(tags = "Stages")
@RequiredArgsConstructor
public class SorterController {

    private final SortingPlanerProducer sortingPlanerProducer;

    private final SortingResult sortingResult;

    @PostMapping("/file")
    @ApiOperation(value = "Create new job for sorting file. Returns the id of the posted job.", notes = "File must exist")
    public ResponseEntity<SortResultDTO> create(@RequestBody SorterRequestDTO sorterRequestDTO) {

        if (StringUtils.isEmpty(sorterRequestDTO.getPath())) {
            throw new RequestValidationException("File path is empty");
        }

        var res = sortingPlanerProducer.scheduleSorting(sorterRequestDTO.getPath());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

    @GetMapping("/file")
    @ApiOperation(value = "Get file path using the id of the sorting job", notes = "get string file path")
    public ResponseEntity<SortResultDTO> getById(@RequestParam("id") int id) {
        return ResponseEntity.ok(sortingResult.getFilePathBySortId(id));
    }
}
