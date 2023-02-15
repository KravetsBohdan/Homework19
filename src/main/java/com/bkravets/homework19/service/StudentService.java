package com.bkravets.homework19.service;

import com.bkravets.homework19.dto.StudentDto;
import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.exception.StudentNotFoundException;
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

    public List<StudentDto> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper.INSTANCE::toStudentDTO)
                .toList();
    }

    public StudentDto getStudent(long id) {
        return studentRepository.findById(id)
                .map(StudentMapper.INSTANCE::toStudentDTO)
                .orElseThrow(StudentNotFoundException::new);
    }


    public StudentDto createStudent(StudentDto studentDTO) {
        Student student = StudentMapper.INSTANCE.toStudent(studentDTO);
        Student studentEntity = studentRepository.save(student);
        return StudentMapper.INSTANCE.toStudentDTO(studentEntity);
    }


    public StudentDto updateStudent(long id, StudentDto studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);

        if (student.getName() != null){
            student.setName(studentDTO.getName());
        }

        if (student.getEmail() != null){
            student.setEmail(studentDTO.getEmail());
        }

        Student studentEntity = studentRepository.save(student);

        return StudentMapper.INSTANCE.toStudentDTO(studentEntity);
    }

    @Transactional
    public void deleteStudent(long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException();
        }

        studentRepository.deleteById(id);
    }


}
