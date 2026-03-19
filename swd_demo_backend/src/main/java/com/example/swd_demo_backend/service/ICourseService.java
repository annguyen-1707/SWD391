package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAllCourses();
    List<Course> getCoursesByCriteria(String status, String sortOption);
    Course getCourseById(Long courseId);
    List<Course> updateCourseStatus(Long courseId, String status, Long adminId);
}
