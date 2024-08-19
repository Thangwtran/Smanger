package com.example.dialog_toolbar.detail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dialog_toolbar.main.ListStudentViewModel;
import com.example.dialog_toolbar.model.Student;

public class DeleteConfirmDialogFragment extends DialogFragment {
    private Student mStudent;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle("Bạn có chắn chắn muốn xóa")
                .setPositiveButton("OK",((dialog, which) -> {
                    ListStudentViewModel.getInstance().remove(mStudent);
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }))
                .setNegativeButton("CANCEL",((dialog, which) -> {}))
                .create();

    }

    public void setStudent(Student student){
        this.mStudent = student;
    }

}
