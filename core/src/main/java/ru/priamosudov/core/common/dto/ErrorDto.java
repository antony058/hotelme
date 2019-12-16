package ru.priamosudov.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorDto {

    @JsonProperty
    private int code;

    @JsonProperty(value = "error_message")
    private String message;
}
