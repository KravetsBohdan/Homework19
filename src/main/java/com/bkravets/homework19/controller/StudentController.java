package com.bkravets.homework19.controller;

import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
       return studentService.getStudents();
    }
}
