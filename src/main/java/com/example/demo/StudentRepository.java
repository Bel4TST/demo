package com.example.demo;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Student;
import org.springframework.data.jpa.repository.Query;


public interface StudentRepository extends JpaRepository<Student, Long> {

        List<Student> findByName(String name);
        List<Student> findByEmailContaining(String email);

    }