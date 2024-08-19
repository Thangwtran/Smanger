package com.example.dialog_toolbar.addition;

import androidx.lifecycle.ViewModel;

import com.example.dialog_toolbar.main.ListStudentViewModel;
import com.example.dialog_toolbar.model.Student;

import java.util.List;

public class NewOrUpdateStudentViewModel extends ViewModel {
    private final Filter mFilter;
    private static NewOrUpdateStudentViewModel instance;


    public NewOrUpdateStudentViewModel() {
        mFilter = new FilterImp();
    }

    public static NewOrUpdateStudentViewModel getInstance() {
        if (instance == null) {
            instance = new NewOrUpdateStudentViewModel();
        }
        return instance;
    }

    public boolean gpaNotValid(String gpa) {
        return !mFilter.isCorrectGpaFormat(gpa);
    }

    public boolean birthDateNotValid(String birthdate) {
        return !mFilter.isCorrectDataFormat(birthdate);
    }

    public boolean checkStringIsEmpty(String data) {
        return mFilter.isFieldEmpty(data);
    }

    public boolean isStudentExisted(String id) {
        return mFilter.isStudentExisted(ListStudentViewModel.getInstance().getStudents()
                .getValue(), id);
    }

    public void addNewStudent(String id, String fullName, String address,
                              String email, String major, String gpa,
                              String gender, String year, String birthDate) {
        Student student = new Student(id);
        setStudentData(student, fullName, address, email, major, gpa, gender, year, birthDate);
        ListStudentViewModel.getInstance().addStudent(student);
    }

    private void setStudentData(Student student, String fullName, String address,
                                String email, String major, String gpa,
                                String gender, String year, String date) {
        student.setFullName(fullName);
        student.setAddress(address);
        student.setEmail(email);
        student.setGpa(Float.parseFloat(gpa));
        student.setMajor(major);
        student.setGender(gender);
        student.setYear(Integer.parseInt(year));
        student.setBirthDate(date);
    }

    public void updateStudentInfo(String id, String fullName, String address,
                                  String email, String major, String gpa,
                                  String gender, String year, String birthDate) {
        List<Student> students = ListStudentViewModel.getInstance().getStudents().getValue();
        Student student = mFilter.findById(students, id);
        setStudentData(student, fullName, address, email, major, gpa, gender, year, birthDate);
        ListStudentViewModel.getInstance().update(student);
    }

}
