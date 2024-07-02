package com.cem.datajpabasic.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    // which service we want to test
    @InjectMocks
    private StudentService studentService;

    // declare the dependencies
    @Mock
    private  StudentRepository repository;
    @Mock
    private  StudentMapper studentMapper;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void should_successfully_save_a_student(){
        // Given
        StudentDto dto = new StudentDto("John",
                "Doe",
                "john@gmail.com",
                1

        );

        Student student = new Student("John",
                "Doe",
                "john@gmail.com",
                20

        );
        Student savedStudent = new Student("John",
                "Doe",
                "john@gmail.com",
                20

        );
        savedStudent.setId(1);

        // Mock the calls
        when(studentMapper.toStudent(dto)).thenReturn(student);
        when(repository.save(student)).thenReturn(savedStudent);
        when(studentMapper.toStudentResponseDto(savedStudent)).thenReturn(new StudentResponseDto(
                "John",
                "Doe",
                "john@gmail.com"));

        // When
        StudentResponseDto responseDto = studentService.saveStudent(dto);


        // Then
        assertEquals(dto.firstname(),responseDto.firstname());
        assertEquals(dto.lastname(),responseDto.lastname());
        assertEquals(dto.email(),responseDto.email());

        verify(studentMapper, times(1))
                .toStudent(dto);
        verify(repository, times(1))
                .save(student);
        verify(studentMapper, times(1))
                .toStudentResponseDto(savedStudent);
    }

    @Test
    public void should_successfully_findAllStudents(){
        // Given
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("John",
                "Doe",
                "john@gmail.com",
                20));


        // Mock the calls
        when(repository.findAll()).thenReturn(studentList);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto("John","Doe","john@gmail.com"));



        // When

        List<StudentResponseDto> resultList= studentService.findAllStudents();



        // Then
        assertEquals(studentList.size(),resultList.size());

        verify(repository, times(1)).findAll();




    }

    @Test
    public void should_return_student_by_id(){

        // Given
        Integer studentId = 1;

        Student student = new Student("John",
                "Doe",
                "john@gmail.com",
                20

        );

        // Mock the calls

        when(repository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDto(student)).thenReturn(new StudentResponseDto("John","Doe","john@gmail.com"));

        // When
       StudentResponseDto responseDto= studentService.findStudentById(studentId);

        // Then
        assertEquals(responseDto.firstname(),student.getFirstname());
        assertEquals(responseDto.lastname(),student.getLastname());
        assertEquals(responseDto.email(),student.getEmail());

        verify(repository,times(1)).findById(studentId);

    }
    @Test
    public void should_find_student_by_name(){

        // Given
        String studentName = "John";
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("John",
                "Doe",
                "john@gmail.com",
                20));



        // Mock the calls
        when(repository.findAllByFirstnameContaining(studentName)).thenReturn(studentList);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto("John","Doe","john@gmail.com"));

        // When
        var responseDto = studentService.findStudentsByName(studentName);

        // Then
        assertEquals(studentList.size(), responseDto.size());
        verify(repository,times(1)).findAllByFirstnameContaining(studentName);

    }
}