package com.bkravets.homework19.service;

import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void createStudent(String name, String email) {

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);

        studentRepository.save(student);
    }

}
