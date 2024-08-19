package com.example.dialog_toolbar.addition;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dialog_toolbar.R;

public class NewStudentActivity extends AppCompatActivity {
    private Spinner spinnerAddress;
    private Spinner spinnerMajor;
    private Spinner spinnerYear;
    private EditText editId;
    private EditText editFullName;
    private EditText editEmail;
    private EditText editGpa;
    private EditText editBirthDate;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private RadioButton rbtnOther;
    private NewOrUpdateStudentViewModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        model = NewOrUpdateStudentViewModel.getInstance();
        setTitle(getString(R.string.title_new_student));
        initViews();
        initSpinner();
    }

    private void initSpinner() {
        spinnerAddress = findViewById(R.id.spinner_address);
        spinnerMajor = findViewById(R.id.spinner_major);
        spinnerYear = findViewById(R.id.spinner_year);
        // tạo các adapter cho spinner
        ArrayAdapter<CharSequence> addressAdapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, R.layout.my_spinner);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, R.layout.my_spinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, R.layout.my_spinner);
        // tạo các drop down item view cho adapter
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn adapter cho spinner
        spinnerAddress.setAdapter(addressAdapter);
        spinnerMajor.setAdapter(majorAdapter);
        spinnerYear.setAdapter(yearAdapter);
    }

    private void initViews() {
        Button btnComplete = findViewById(R.id.btn_finish);
        btnComplete.setOnClickListener(v -> createStudent());

        rbtnMale = findViewById(R.id.radioMale);
        rbtnFemale = findViewById(R.id.radioFemale);
        rbtnOther = findViewById(R.id.radioOther);
        editBirthDate = findViewById(R.id.edit_birth_date);
        editId = findViewById(R.id.edit_student_id);
        editFullName = findViewById(R.id.edit_full_name);
        editEmail = findViewById(R.id.edit_email);
        editGpa = findViewById(R.id.edit_gpa);
        editBirthDate.setOnClickListener(this::showViewNormal);
        editGpa.setOnClickListener(this::showViewNormal);
    }

    private void createStudent() {
        boolean isDataClear = true;
        String studentId = editId.getText().toString();
        String fullName = editFullName.getText().toString();
        String gender = getGender();
        String email = editEmail.getText().toString();
        String gpa = editGpa.getText().toString();
        String birthDate = editBirthDate.getText().toString();
        String address = spinnerAddress.getSelectedItem().toString();
        String year = spinnerYear.getSelectedItem().toString();
        String major = spinnerMajor.getSelectedItem().toString();
        if (model.checkStringIsEmpty(studentId)) {
            showErrorHint(editId, R.string.hint_id_error);
        }
        if (model.checkStringIsEmpty(fullName)) {
            showErrorHint(editFullName, R.string.hint_fullname_error);
            isDataClear = false;
        }
        if (model.checkStringIsEmpty(birthDate)) {
            showErrorHint(editBirthDate, R.string.hint_birth_date_empty);
            isDataClear = false;
        } else if (model.birthDateNotValid(birthDate)) {
            showErrorView(editBirthDate);
            isDataClear = false;
        }
        if (model.checkStringIsEmpty(email)) {
            showErrorHint(editEmail, R.string.hint_email_empty);
            isDataClear = false;
        }
        if (model.checkStringIsEmpty(gpa)) {
            showErrorHint(editGpa, R.string.hint_gpa_empty);
            isDataClear = false;
        } else if (model.gpaNotValid(gpa)) {
            showErrorView(editGpa);
            isDataClear = false;
        }
        if (!isDataClear) {
            return;
        }
        if (!model.isStudentExisted(studentId)) {
            model.addNewStudent(studentId, fullName, address,
                    email, major, gpa, gender, year, birthDate);
            finish();
        } else {
            Toast.makeText(this,
                    getString(R.string.text_student_existed),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @NonNull
    private String getGender() {
        if (rbtnFemale.isSelected()) {
            return "Nữ";
        } else if (rbtnMale.isSelected()) {
            return "Nam";
        } else if (rbtnOther.isSelected()) {
            return "Khác";
        }
        return "";
    }
    private void showErrorView(EditText view) {
        view.setBackgroundResource(R.drawable.invalid_text);
    }

    private void showViewNormal(View view) {
        view.setBackgroundResource(R.drawable.normal_text);
    }

    private void showErrorHint(EditText view, int stringId) {
        view.setHint(getString(stringId));
        view.setHintTextColor(getColor(R.color.text_color_red));
    }
}
