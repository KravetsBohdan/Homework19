package com.bkravets.homework19.repository;

import com.bkravets.homework19.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

