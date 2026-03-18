-- Set safe updates off if needed
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

-- Clean up existing data and reset auto-increment IDs to prevent duplicates on rerun
TRUNCATE TABLE courses;
TRUNCATE TABLE content_administrator;
TRUNCATE TABLE platform_administrator;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE verification_tokens;
TRUNCATE TABLE notifications;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. Insert Roles
INSERT INTO roles (role_name, description) VALUES ('PlatformAdmin', 'Can create users');
INSERT INTO roles (role_name, description) VALUES ('ContentAdmin', 'Can approve courses');

-- 2. Insert Users (Password stored as plain text '123456' or hash depending on your setup)
INSERT INTO users (username, email, password, status, created_at) VALUES ('platform_admin', 'platform@swd.com', '123456', 'ACTIVE', NOW());
INSERT INTO users (username, email, password, status, created_at) VALUES ('content_admin', 'content@swd.com', '123456', 'ACTIVE', NOW());

-- 3. Assign Roles to Users Maps to user_roles (user_id, role_id)
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);

-- 4. Map Users to Child Tables (JOINED Inheritance Strategy)
INSERT INTO platform_administrator (user_id) VALUES (1);
INSERT INTO content_administrator (user_id, department) VALUES (2, 'Testing & QA');

-- 5. Insert Mock Courses (Nhiều dòng)
INSERT INTO courses (course_code, name, description, author, credits, category, status, created_at, updated_at) VALUES 
('CS101', 'Intro to Computer Science', 'A fundamental course covering programming basics, algorithms, and data structures using Python.', 'Dr. Alan Turing', 3, 'Computer Science', 'PENDING', NOW(), NOW()),
('MA202', 'Advanced Mathematics', 'Calculus, linear algebra, and discrete mathematics for science students.', 'Prof. John von Neumann', 4, 'Mathematics', 'PENDING', NOW(), NOW()),
('SWD391', 'Software Architecture and Design', 'Learn to design scalable, maintainable, and robust enterprise software systems using design patterns.', 'KhaiNH', 3, 'Software Engineering', 'APPROVED', NOW(), NOW()),
('PRJ301', 'Java Web Development', 'Building web applications with Servlets, JSPs, and Spring Boot framework.', 'Dr. Java Lover', 3, 'Software Engineering', 'REJECTED', NOW(), NOW()),
('AI401', 'Artificial Intelligence', 'Introduction to Machine Learning, neural networks, and AI algorithms.', 'Sam Altman', 4, 'Computer Science', 'PENDING', NOW(), NOW()),
('DB201', 'Database Management Systems', 'Relational database design, SQL queries, and normalization techniques.', 'Edgar F. Codd', 3, 'Database Management', 'PENDING', NOW(), NOW()),
('NET501', 'Computer Networks', 'Subnetting, OSI model, TCP/IP architecture, and routing protocols.', 'Vint Cerf', 3, 'Networking', 'APPROVED', NOW(), NOW()),
('SEC601', 'Cybersecurity Basics', 'Principles of cryptography, ethical hacking, and securing web applications.', 'Alice & Bob', 3, 'Security', 'PENDING', NOW(), NOW()),
('ML701', 'Deep Learning', 'Advanced deep learning techniques with TensorFlow and PyTorch frameworks.', 'Yann LeCun', 4, 'Computer Science', 'PENDING', NOW(), NOW()),
('IOT801', 'Internet of Things', 'Sensor networks, MQTT protocols, Arduinos, and automation systems.', 'Kevin Ashton', 3, 'Electronics', 'PENDING', NOW(), NOW()),
('UX901', 'UI/UX Design', 'Design thinking, prototyping, and layout principles using Figma.', 'Don Norman', 3, 'Design', 'PENDING', NOW(), NOW()),
('MOB201', 'Mobile App Development', 'Building cross-platform mobile apps with React Native and Expo.', 'Dan Abramov', 3, 'Software Engineering', 'PENDING', NOW(), NOW());
