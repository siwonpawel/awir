package com.github.siwonpawel.awir.controllers.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse
{

    private final int status;
    private final String message;

}
