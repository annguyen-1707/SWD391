package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.Course;
import com.example.swd_demo_backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> getCoursesByCriteria(String status, String sortOption) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortOption != null && !sortOption.isEmpty() ? sortOption : "createdAt");
        
        if (status != null && !status.isEmpty()) {
            // A dynamic query should ideally be used, but since we only filter by status, this works
            // If repository method doesn't support Sort directly with custom queries, we can fetch and sort.
            // Using standard findAll(Sort) and filtering in memory or custom query with Sort.
            // For simplicity, we filter in memory if status is provided, but ideally it's in DB.
            return courseRepository.findByStatus(status).stream().sorted((c1, c2) -> {
                if ("name".equals(sortOption)) return c1.getName().compareToIgnoreCase(c2.getName());
                return c1.getCreatedAt().compareTo(c2.getCreatedAt());
            }).toList();
        }
        return courseRepository.findAll(sort);
    }

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    @Transactional
    public List<Course> updateCourseStatus(Long courseId, String status, Long adminId) {
        Course course = getCourseById(courseId);
        course.setStatus(status);
        course.setApprovedBy(adminId);
        course.setUpdatedAt(LocalDateTime.now());
        course.setApprovedAt(LocalDateTime.now());
        courseRepository.save(course);
        
        return getAllCourses();
    }
}
