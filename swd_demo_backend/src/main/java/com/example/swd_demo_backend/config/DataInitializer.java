package com.example.swd_demo_backend.config;

import com.example.swd_demo_backend.entity.*;
import com.example.swd_demo_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

// @Component  <-- Commented out because we are using MySQL and data.sql to initialize data now
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role r1 = new Role();
            r1.setRoleName("PlatformAdmin");
            r1.setDescription("Can create users");
            roleRepository.save(r1);

            Role r2 = new Role();
            r2.setRoleName("ContentAdmin");
            r2.setDescription("Can approve courses");
            roleRepository.save(r2);
        }

        if (courseRepository.count() == 0) {
            Course c1 = new Course();
            c1.setCourseCode("CS101");
            c1.setName("Intro to Computer Science");
            c1.setDescription("A fundamental course covering programming basics, algorithms, and data structures.");
            c1.setAuthor("Dr. Alan Turing");
            c1.setCredits(3);
            c1.setCategory("Computer Science");
            c1.setStatus("PENDING");
            
            Course c2 = new Course();
            c2.setCourseCode("MA202");
            c2.setName("Advanced Mathematics");
            c2.setDescription("Calculus, linear algebra, and discrete mathematics for science students.");
            c2.setAuthor("Prof. John von Neumann");
            c2.setCredits(4);
            c2.setCategory("Mathematics");
            c2.setStatus("PENDING");

            Course c3 = new Course();
            c3.setCourseCode("SWD391");
            c3.setName("Software Architecture and Design");
            c3.setDescription("Learn to design scalable, maintainable, and robust enterprise software systems using design patterns.");
            c3.setAuthor("KhaiNH");
            c3.setCredits(3);
            c3.setCategory("Software Engineering");
            c3.setStatus("APPROVED");

            courseRepository.saveAll(List.of(c1, c2, c3));
        }
    }
}
