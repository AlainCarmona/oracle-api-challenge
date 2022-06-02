package com.oracle.apichallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiChallengeResponseDto {
    private int code;
    private String message;
    private Object body;
}
