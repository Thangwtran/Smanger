package com.example.dialog_toolbar.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.addition.NewStudentActivity;
import com.example.dialog_toolbar.detail.StudentDetailFragment;
import com.example.dialog_toolbar.edit.EditStudentFragment;
import com.example.dialog_toolbar.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements
        StudentDetailFragment.OnEditStudentClickListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        StudentViewModel studentViewModel = new ViewModelProvider(this)
                .get(StudentViewModel.class);
        studentViewModel.getSelectedStudent()
                .observe(this,this::createDetailFragment);
    }

    private void initViews() {
        FloatingActionButton btnNewStudent = findViewById(R.id.btn_add_new);
        btnNewStudent.setOnClickListener(this);
    }

    private void createDetailFragment(Student student) {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(R.id.fragment_container,
                        StudentDetailFragment.getInstance(this))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(R.id.fragment_container, EditStudentFragment.class,null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, NewStudentActivity.class);
        startActivity(intent);
    }
}