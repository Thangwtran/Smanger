package com.example.dialog_toolbar.controller;

import com.example.dialog_toolbar.model.Student;

import java.util.List;

public interface SearchController {
    List<Student> findByName(List<Student> students, String name);

    List<Student> findById(List<Student> students, String id);

    List<Student> findByGpa(List<Student> students, float gpa);
}
