package com.bigfile.sort.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SorterRequestDTO {

    @ApiModelProperty(
            value = "Path to file to be sorted;",
            name = "path",
            dataType = "String",
            example = "some_big_file_test",
            position = 2)
    @JsonProperty("path")
    private String path;
}
