package com.example.dialog_toolbar.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dialog_toolbar.model.Student;
import com.example.dialog_toolbar.parser.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class ListStudentViewModel extends ViewModel {
    private final MutableLiveData<List<Student>> mStudentsLiveData;
    private static ListStudentViewModel instance;
    private final List<Student> mStudentList;

    public ListStudentViewModel() {
        mStudentList = new ArrayList<>();
        mStudentsLiveData = new MutableLiveData<>();
    }


    public static ListStudentViewModel getInstance() {
        if (instance == null) {
            instance = new ListStudentViewModel();
        }
        return instance;
    }

    public LiveData<List<Student>> getStudents() {
        return mStudentsLiveData;
    }

    public void loadData(Context context) {
        if(mStudentList.isEmpty()){
            JsonParser parser = new JsonParser();
            String json = parser.getJsonFromFile(context);
            List<Student> studentList = parser.loadData(json);
            if (json != null) {
                mStudentList.clear();
                mStudentList.addAll(studentList);
                if (!mStudentList.isEmpty()) {
                    mStudentsLiveData.setValue(studentList);
                }
            }
        }

    }
    public void addStudent(Student student) {
        mStudentList.add(student);
        mStudentsLiveData.setValue(mStudentList);
    }

    public void update(Student student) {
        int index = mStudentList.indexOf(student);
        mStudentList.remove(index);
        mStudentList.add(index, student);
        mStudentsLiveData.setValue(mStudentList);
    }

    public void remove(Student student){
        mStudentList.remove(student);
        mStudentsLiveData.setValue(mStudentList);
    }

}
