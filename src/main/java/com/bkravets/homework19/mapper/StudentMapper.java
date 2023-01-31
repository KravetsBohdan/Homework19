package com.bkravets.homework19.mapper;

import com.bkravets.homework19.dto.StudentDTO;
import com.bkravets.homework19.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO toStudentDTO(Student student);


    Student toStudent(StudentDTO studentDTO);
}
