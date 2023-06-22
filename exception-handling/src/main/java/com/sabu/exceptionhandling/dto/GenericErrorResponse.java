package com.sabu.exceptionhandling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericErrorResponse {

    private String message;

    private String code;

}
