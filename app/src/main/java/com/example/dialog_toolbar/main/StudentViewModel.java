package com.example.dialog_toolbar.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dialog_toolbar.model.Student;

public class StudentViewModel extends ViewModel {
    private final MutableLiveData<Student> selectedStudent = new MutableLiveData<>();

    public void setSelectedStudent(Student student){
        selectedStudent.setValue(student);
    }

    public LiveData<Student> getSelectedStudent(){
        return selectedStudent;
    }
}
