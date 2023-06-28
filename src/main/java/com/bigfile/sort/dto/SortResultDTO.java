package com.bigfile.sort.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortResultDTO {

    @ApiModelProperty(
            value = "Stage id;",
            name = "id",
            dataType = "Long",
            example = "1",
            position = 0)
    @JsonProperty("id")
    private long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(
            value = "Path to file to be sorted;",
            name = "original_path",
            dataType = "String",
            example = "some_big_file_test",
            position = 1)
    @JsonProperty("original_path")
    private String originalPath;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(
            value = "Path to the sorted file;",
            name = "result_path",
            dataType = "String",
            example = "sorted_some_big_file_test",
            position = 2)
    @JsonProperty("result_path")
    private String resultPath;

    @ApiModelProperty(
            value = "File sort status;",
            name = "ready",
            dataType = "boolean",
            example = "true",
            position = 3)
    @JsonProperty("ready")
    private boolean ready;

}
