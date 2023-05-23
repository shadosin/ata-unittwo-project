package com.kenzie.unit.two.employee.service;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;


@ExcludeFromJacocoGeneratedReport
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
