package com.example.dialog_toolbar.controller;

import com.example.dialog_toolbar.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchControllerImp implements SearchController{
    @Override
    public List<Student> findByName(List<Student> students, String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if(isMatch(s.getFirstName(),name)){
                result.add(s);
            }
        }
        return result;
    }
    private boolean isMatch (String firstName,String key){
        String pattern = ".*" +key + ".*";
        Pattern pat = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(firstName);
        return matcher.matches();
    }

    @Override
    public List<Student> findById(List<Student> students, String id) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if(isMatch(s.getId(),id)){
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public List<Student> findByGpa(List<Student> students, float gpa) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if(s.getGpa() == gpa){
                result.add(s);
            }
        }
        return result;
    }
}
