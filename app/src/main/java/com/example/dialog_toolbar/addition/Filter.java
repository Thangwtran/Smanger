package com.example.dialog_toolbar.addition;

import com.example.dialog_toolbar.model.Student;

import java.util.List;

public interface Filter {
    boolean isFieldEmpty(String data);

    boolean isCorrectDataFormat(String data);

    boolean isCorrectGpaFormat(String data);

    boolean isStudentExisted(List<Student> students, String id);

    Student findById(List<Student> students, String id);
}
