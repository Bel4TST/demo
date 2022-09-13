package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Student;
import com.example.demo.StudentRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(value = "email", required = false)
            String email, HttpSession session)
    {
        try {
            List<Student> students = new ArrayList<Student>();
            if (email == null)
               studentRepository.findAll().forEach(students::add);
            else
               studentRepository.findByEmailContaining(email).forEach(students::add);
            if (students.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id, HttpSession session) {
        Optional<Student>studentData =studentRepository.findById(id);
        if (studentData.isPresent()) {
            return new ResponseEntity<>(studentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/students_reg")
    public ResponseEntity<Student> createStudent(@RequestBody Student student,HttpServletRequest request) {
        try {
            Student _student =studentRepository
                    .save(new Student(student.getDescription(),student.getEmail(),student.getName(),student.getPassword()));
           System.out.println(_student);
            return new ResponseEntity<>(_student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student,HttpServletRequest request) {
        Optional<Student>studentData =studentRepository.findById(id);
        if (studentData.isPresent()) {
            Student _student =studentData.get();
            _student.setName(student.getName());
            _student.setEmail(student.getEmail());
            _student.setDescription(student.getDescription());
            _student.setPassword(student.getPassword());
            return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id,HttpServletRequest request) {
        try {
           studentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents(HttpServletRequest request) {
        try {
           studentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  /*  @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> logout(HttpSession session){

        String userName=(String)session.getAttribute("name");
        System.out.println("name: " + userName);
        session.invalidate();
        return ResponseEntity.ok("user logged out");

    }*/
}
