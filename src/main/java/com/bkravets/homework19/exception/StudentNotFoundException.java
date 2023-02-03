package com.bkravets.homework19.exception;

public class StudentNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Student not found";

    public StudentNotFoundException() {
        super(MESSAGE);
    }
}
