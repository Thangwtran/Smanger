package com.example.dialog_toolbar.controller;

import com.example.dialog_toolbar.model.Student;

import java.util.List;

public interface SortController {

    void sortByNameASC(List<Student> students);

    void sortByNameDESC(List<Student> students);

    void sortByGpaASC(List<Student> students);

    void sortByGpaDESC(List<Student> students);

    void sortByIdASC(List<Student> students);

    void sortByIdDESC(List<Student> students);
}
