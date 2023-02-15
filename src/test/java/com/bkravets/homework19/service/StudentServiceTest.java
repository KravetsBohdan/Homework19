package com.bkravets.homework19.service;

import com.bkravets.homework19.dto.StudentDto;
import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.exception.StudentNotFoundException;
import com.bkravets.homework19.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDto studentDto;


    @BeforeEach
    void setUp() {
        student = new Student(1L, "Bob", "mail.com", new ArrayList<>());
        studentDto = new StudentDto("Bob", "mail.com");
    }

    @Test
    void shouldGetStudents() {
        // given
        List<Student> students = List.of(student);
        doReturn(students).when(studentRepository).findAll();

        // when
        List<StudentDto> actualStudentsDtos = studentService.getStudents();

        // then
        assertThat(actualStudentsDtos).containsExactly(studentDto);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void shouldGetStudent() {
        // given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        //  when
        StudentDto actualStudentDto = studentService.getStudent(anyLong());

        // then
        assertEquals(studentDto, actualStudentDto);
        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        // given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> studentService.getStudent(anyLong()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, times(1)).findById(anyLong());
    }

    @Test
    void shouldCreateStudent() {
       // given
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        //when
        StudentDto createdStudent = studentService.createStudent(studentDto);

        // then
        assertThat(createdStudent).isEqualTo(studentDto);
        verify(studentRepository, times(1)).save(any(Student.class));
    }


    @Test
    void shouldUpdateStudent() {
        // given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // when
        StudentDto actualStudentDto = studentService.updateStudent(1, studentDto);

        // then
        assertThat(actualStudentDto).isEqualTo(studentDto);
        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentToUpdateNotFound() {
        // given
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> studentService.updateStudent(anyLong(), studentDto))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, times(1)).findById(anyLong());
        verify(studentRepository, times(0)).save(any(Student.class));
    }

    @Test
    void shouldDeleteStudent() {
        // given
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(studentRepository).deleteById(anyLong());

        // when
        studentService.deleteStudent(anyLong());

        // then
        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void shouldThrowExceptionWhenStudentToDeleteNotFound() {
        // given
        when(studentRepository.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> studentService.deleteStudent(anyLong()))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, times(1)).existsById(anyLong());
        verify(studentRepository, times(0)).deleteById(anyLong());
    }
}