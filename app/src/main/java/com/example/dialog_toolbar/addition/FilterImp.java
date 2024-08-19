package com.example.dialog_toolbar.addition;

import com.example.dialog_toolbar.model.Student;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterImp implements Filter {
    @Override
    public boolean isFieldEmpty(String data) {
        return data.trim().isEmpty();
    }

    @Override
    public boolean isCorrectDataFormat(String data) {
        String format = "\\d{2}/\\d{2}/\\d{4}";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

    @Override
    public boolean isCorrectGpaFormat(String data) {
        String format = "\\d.\\d{1,2}";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(data);
        if (matcher.matches()) {
            float gpa = Float.parseFloat(data);
            return gpa <= 4.0 && gpa >= 0;
        }
        return false;
    }

    @Override
    public boolean isStudentExisted(List<Student> students, String id) {
        if(students.contains(new Student(id))){
            for (Student s : students) {
                if (s.getId().compareToIgnoreCase(id) == 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Student findById(List<Student> students, String id) {
        if(students.contains(new Student(id))){
            for (Student s : students) {
                if (s.getId().compareToIgnoreCase(id) == 0){
                    return s;
                }
            }
        }
        return null;
    }
}
