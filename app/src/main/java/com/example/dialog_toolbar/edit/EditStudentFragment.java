package com.example.dialog_toolbar.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.addition.NewOrUpdateStudentViewModel;
import com.example.dialog_toolbar.main.StudentViewModel;
import com.example.dialog_toolbar.model.Student;
import com.example.dialog_toolbar.utils.Utils;

public class EditStudentFragment extends Fragment implements View.OnFocusChangeListener {
    private Toolbar toolbar;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_student, container, false);
        initViews(view);
        initSpinner(view);
        addFocusListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = NewOrUpdateStudentViewModel.getInstance();
        toolbar = view.findViewById(R.id.toolbar_edit);
        toolbar.setTitle(getString(R.string.edit_infor));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(
                v->requireActivity().getOnBackPressedDispatcher().onBackPressed());
        toolbar.inflateMenu(R.menu.done_menu);
        updateToolbar(false);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_done) {
                boolean result = updateStudent();
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
                return result;
            }
            return false;
        });
        new ViewModelProvider(requireActivity()).get(StudentViewModel.class)
                .getSelectedStudent().observe(getViewLifecycleOwner(), this::showData);
    }

    private void showData(Student student) {
        editId.setText(student.getId().toUpperCase());
        editFullName.setText(student.getFullName());
        editBirthDate.setText(student.getBirthDate());
        editEmail.setText(student.getEmail());
        editGpa.setText(String.valueOf(student.getGpa()));
        int addressPos = Utils.getAdapterPosition(spinnerAddress.getAdapter(),
                student.getAddress());
        spinnerAddress.setSelection(addressPos);
        int majorPos = Utils.getAdapterPosition(spinnerMajor.getAdapter(),
                student.getMajor());
        spinnerMajor.setSelection(majorPos);
        spinnerYear.setSelection(student.getYear() - 1);
        if (student.getGender().toLowerCase().compareTo("nam") == 0) {
            rbtnMale.setChecked(true);
        } else if (student.getGender().toLowerCase().compareTo("nữ") == 0) {
            rbtnFemale.setChecked(true);
        } else {
            rbtnOther.setChecked(true);
        }
    }

    private boolean updateStudent() {
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

        if(model.checkStringIsEmpty(fullName)){
            showErrorHint(editFullName,R.string.hint_fullname_error);
            isDataClear = false;
        }
        if(model.checkStringIsEmpty(birthDate)){
            showErrorHint(editBirthDate,R.string.hint_birth_date_empty);
            isDataClear = false;
        }else if(model.birthDateNotValid(birthDate)){
            showErrorView(editBirthDate);
            isDataClear = false;
        }
        if(model.checkStringIsEmpty(email)){
            showErrorHint(editEmail,R.string.hint_email_empty);
            isDataClear = false;
        }
        if(model.checkStringIsEmpty(gpa)){
            showErrorHint(editGpa,R.string.hint_gpa_empty);
            isDataClear = false;
        } else if (model.gpaNotValid(gpa)) {
            showErrorView(editGpa);
            isDataClear = false;
        }
        if(isDataClear){
            model.updateStudentInfo(studentId, fullName, address,
                    email, major, gpa, gender, year, birthDate);
            return true;
        }
        return false;
    }

    private void showErrorView(EditText editView) {
        editView.setBackgroundResource(R.drawable.invalid_text);
    }

    private void showErrorHint(EditText editText, int stringId) {
        editText.setHint(getString(stringId));
        editText.setHintTextColor(requireContext().getColor(R.color.text_color_red));
    }

    private String getGender() {
        if (rbtnMale.isSelected()) {
            return "Nam";
        } else if (rbtnFemale.isSelected()) {
            return "Nữ";
        } else if (rbtnOther.isSelected()) {
            return "Khác";
        } else {
            return "";
        }
    }

    private void addFocusListener() {
        editId.setOnFocusChangeListener(this);
        rbtnMale.setOnFocusChangeListener(this);
        rbtnFemale.setOnFocusChangeListener(this);
        rbtnOther.setOnFocusChangeListener(this);
        editBirthDate.setOnFocusChangeListener(this);
        editFullName.setOnFocusChangeListener(this);
        editEmail.setOnFocusChangeListener(this);
        editGpa.setOnFocusChangeListener(this);
        editBirthDate.setOnFocusChangeListener(this);
        editGpa.setOnFocusChangeListener(this);
    }

    private void initSpinner(View view) {
        // tạo các spinner
        spinnerAddress = view.findViewById(R.id.spinner_address);
        spinnerMajor = view.findViewById(R.id.spinner_major);
        spinnerYear = view.findViewById(R.id.spinner_year);
        // tạo các adapter cho spinner
        ArrayAdapter<CharSequence> addressAdapter = ArrayAdapter
                .createFromResource(requireContext(), R.array.city_array, R.layout.my_spinner);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter
                .createFromResource(requireContext(), R.array.major_array, R.layout.my_spinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter
                .createFromResource(requireContext(), R.array.year_array, R.layout.my_spinner);
        // tạo các drop down item view cho adapter
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // gắn adapter cho spinner
        spinnerAddress.setAdapter(addressAdapter);
        spinnerMajor.setAdapter(majorAdapter);
        spinnerYear.setAdapter(yearAdapter);
    }

    private void initViews(View view) {
        editId = view.findViewById(R.id.edit_student_id);
        editId.setEnabled(false); // ?
        rbtnMale = view.findViewById(R.id.radioMale);
        rbtnFemale = view.findViewById(R.id.radioFemale);
        rbtnOther = view.findViewById(R.id.radioOther);
        editBirthDate = view.findViewById(R.id.edit_birth_date);
        editFullName = view.findViewById(R.id.edit_full_name);
        editEmail = view.findViewById(R.id.edit_email);
        editGpa = view.findViewById(R.id.edit_gpa);
        editBirthDate.setOnClickListener(this::showViewNormal);
        editGpa.setOnClickListener(this::showViewNormal);
    }

    private void showViewNormal(View view) {
        view.setBackgroundResource(R.drawable.normal_text);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            updateToolbar(true);
        }
    }

    private void updateToolbar(boolean isVisible) {
        MenuItem item = toolbar.getMenu().findItem(R.id.action_done);
        item.setVisible(isVisible);
    }
}
