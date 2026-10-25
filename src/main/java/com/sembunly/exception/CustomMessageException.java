package com.sembunly.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomMessageException extends RuntimeException {

    @JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private String code;



}
