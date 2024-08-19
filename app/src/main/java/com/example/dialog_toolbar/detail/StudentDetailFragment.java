package com.example.dialog_toolbar.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.main.StudentViewModel;
import com.example.dialog_toolbar.model.Student;
import com.example.dialog_toolbar.utils.Utils;

public class StudentDetailFragment extends Fragment {
    private TextView textStudentId;
    private TextView textFullName;
    private TextView textAddress;
    private TextView textEmail;
    private TextView textMajor;
    private TextView textBirthDate;
    private TextView textGpa;
    private TextView textYear;
    private TextView textGender;
    private ImageView imageAvatar;
    private Toolbar toolbar;
    private OnEditStudentClickListener listener;
    private DeleteConfirmDialogFragment mDeleteConfirmDialog;

    public static StudentDetailFragment getInstance(OnEditStudentClickListener listener){
        StudentDetailFragment fragment = new StudentDetailFragment();
        fragment.listener = listener;
        fragment.mDeleteConfirmDialog = new DeleteConfirmDialogFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_detail,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createToolbar(view);
        new ViewModelProvider(requireActivity()).get(StudentViewModel.class)
                .getSelectedStudent().observe(getViewLifecycleOwner(),student -> {
                    mDeleteConfirmDialog.setStudent(student);
                    showData(student);
                });
    }

    /** @noinspection deprecation*/
    private void createToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24);
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.action_edit){
                startEditScreen();
                return true;
            } else if (item.getItemId() == R.id.action_delete) {
                String tag = "CONFIRM_DIALOG_TAG";
                mDeleteConfirmDialog.show(getChildFragmentManager(),tag);
            }
            return super.onOptionsItemSelected(item);
        });
    }

    private void startEditScreen() {
        listener.onClick();
    }

    @SuppressLint("SetTextI18n")
    private void showData(Student student) {
        textStudentId.setText(getString(R.string.text_id) + " " + student.getId());
        textFullName.setText(getString(R.string.text_fullname) + " " + student.getFullName());
        textAddress.setText(getString(R.string.text_address) + " " + student.getAddress());
        textEmail.setText(getString(R.string.text_email) + " " + student.getEmail());
        textMajor.setText(getString(R.string.text_major) + " " + student.getMajor());
        textBirthDate.setText(getString(R.string.text_birth_date) + " " + student.getBirthDate());
        textGpa.setText(getString(R.string.text_gpa) + " " + student.getGpa());
        textYear.setText(getString(R.string.text_year) + " " + student.getYear());
        textGender.setText(getString(R.string.text_gender) + " " + student.getGender());
        imageAvatar.setImageResource(Utils.getImageResourceId(student.getGender()));
    }

    private void initViews(View view) {
        textStudentId = view.findViewById(R.id.text_id_detail);
        textFullName = view.findViewById(R.id.text_full_name_detail);
        textAddress = view.findViewById(R.id.text_address_detail);
        textEmail = view.findViewById(R.id.text_email_detail);
        textMajor = view.findViewById(R.id.text_major_detail);
        textBirthDate = view.findViewById(R.id.text_birth_date_detail);
        textGpa = view.findViewById(R.id.text_gpa_detail);
        textYear = view.findViewById(R.id.text_year_detail);
        textGender = view.findViewById(R.id.text_gender_detail);
        imageAvatar = view.findViewById(R.id.image_detail);
        toolbar = view.findViewById(R.id.toolbar_detail);
    }

    public interface OnEditStudentClickListener{
        void onClick();
    }
}
