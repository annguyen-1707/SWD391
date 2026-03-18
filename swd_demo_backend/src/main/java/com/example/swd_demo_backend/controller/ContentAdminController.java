package com.example.swd_demo_backend.controller;

import com.example.swd_demo_backend.entity.Course;
import com.example.swd_demo_backend.service.CourseService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // For demo purposes
public class ContentAdminController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourseDashboard(
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sortOption", required = false) String sortOption) {
        
        List<Course> courses;
        if (status != null || sortOption != null) {
            courses = courseService.getCoursesByCriteria(status, sortOption);
        } else {
            courses = courseService.getAllCourses();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> viewCourseDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<List<Course>> approveRejectCourse(
            @PathVariable("id") Long id,
            @RequestBody UpdateStatusRequest request) {
        List<Course> updatedList = courseService.updateCourseStatus(id, request.getStatus(), request.getAdminId());
        return ResponseEntity.ok(updatedList);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage().toLowerCase().contains("permission") || ex.getMessage().toLowerCase().contains("role")) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @Data
    public static class UpdateStatusRequest {
        private String status;
        private Long adminId; // In a real app, this comes from the authenticated session
    }
}
