package com.bkravets.homework19.service;

import com.bkravets.homework19.dto.StudentDTO;
import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.mapper.StudentMapper;
import com.bkravets.homework19.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper.INSTANCE::toStudentDTO)
                .toList();
    }

    public StudentDTO getStudent(long id) {
        return studentRepository.findById(id)
                .map(StudentMapper.INSTANCE::toStudentDTO)
                .orElseThrow();
    }


    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.INSTANCE.toStudent(studentDTO);
        studentRepository.save(student);
        return studentDTO;
    }

    @Transactional
    public StudentDTO updateStudent(long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id).orElseThrow();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        studentRepository.save(student);

        return studentDTO;
    }

    @Transactional
    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student not found");
        }

        studentRepository.deleteById(id);
    }


}
