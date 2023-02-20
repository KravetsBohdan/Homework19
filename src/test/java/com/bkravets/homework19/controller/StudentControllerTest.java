package com.bkravets.homework19.controller;

import com.bkravets.homework19.entity.Student;
import com.bkravets.homework19.repository.StudentRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StudentControllerTest {

    @LocalServerPort
    private int springBootPort = 0;

    private static final String STUDENTS_URL = "/students";
    private static final String STUDENT_URL = "/students/{id}";

    private static final Student STUDENT_ENTITY = new Student(1L, "Bob", "mail.com", new ArrayList<>());

    private static final String STUDENT_JSON = """
            {
                "name": "Bob",
                "email": "mail.com"
            }
            """;

    @Autowired
    private StudentRepository studentRepository;

    private long savedStudentId;

    @BeforeEach
    void beforeEach() {
        Student savedStudent = studentRepository.save(STUDENT_ENTITY);
        savedStudentId = savedStudent.getId();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldGetStudents() {
    given()
            .contentType(ContentType.JSON)
            .port(springBootPort)
    .when()
            .get(STUDENTS_URL)
    .then()
                .statusCode(HttpStatus.OK.value())
                .body("[0].name", equalTo("Bob"))
                .body("[0].email", equalTo("mail.com"));
    }

    @Test
    void shouldGetStudent() {
        given()
                .contentType(ContentType.JSON)
                .port(springBootPort)
                .pathParam("id", savedStudentId)
        .when()
                .get(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Bob"))
                .body("email", equalTo("mail.com"));
    }

    @Test
    void shouldGetErrorMessageWhenStudentNotFound() {
        given()
                .contentType(ContentType.JSON)
                .port(springBootPort)
                .pathParam("id", -1)
        .when()
                .get(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Student not found"));
    }

    @Test
    void shouldCreateStudent() {
        given()
                .body(STUDENT_JSON)
                .contentType(ContentType.JSON)
                .port(springBootPort)
        .when()
                .post(STUDENTS_URL)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Bob"))
                .body("email", equalTo("mail.com"));
    }

    @Test
    void shouldUpdateStudent() {
        given()
                .body("""
                        {
                            "name": "Denis",
                            "email": "mail.com"
                        }
                        """)
                .contentType(ContentType.JSON)
                .port(springBootPort)
                .pathParam("id", savedStudentId)
        .when()
                .put(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Denis"))
                .body("email", equalTo("mail.com"));
    }

    @Test
    void shouldGetErrorMessageWhenStudentToUpdateNotFound() {
        given()
                .body("""
                        {
                            "name": "Denis",
                            "email": "mail.com"
                        }
                        """)
                .contentType(ContentType.JSON)
                .port(springBootPort)
                .pathParam("id", -1)
        .when()
                .put(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Student not found"));
    }

    @Test
    void shouldDeleteStudent() {
        given()
                .port(springBootPort)
                .pathParam("id", savedStudentId)
        .when()
                .delete(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void shouldGetErrorMessageWhenStudentToDeleteNotFound() {
        given()
                .port(springBootPort)
                .pathParam("id", -1)
        .when()
                .delete(STUDENT_URL)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Student not found"));
    }
}