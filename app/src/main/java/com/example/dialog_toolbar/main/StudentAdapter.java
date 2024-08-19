package com.example.dialog_toolbar.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.model.Student;
import com.example.dialog_toolbar.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends
        RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final List<Student> mStudents;
    private final OnItemClickListener mListener;

    public StudentAdapter(OnItemClickListener listener) {
        mStudents = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mStudents.get(position),mListener);

    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStudent(List<Student> students) {
        mStudents.clear();
        mStudents.addAll(students);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemFullName;
        private TextView itemGpa;
        private TextView itemId;

        private final View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            initViews(itemView);
        }

        private void initViews(View itemView) {
            itemImage = itemView.findViewById(R.id.item_image);
            itemFullName = itemView.findViewById(R.id.text_item_name);
            itemId = itemView.findViewById(R.id.text_item_id);
            itemGpa = itemView.findViewById(R.id.text_item_gpa);
        }

        public void bind(Student student,OnItemClickListener listener) {
            itemFullName.setText(student.getFullName());
            itemId.setText(student.getId());
            itemGpa.setText(String.valueOf(student.getGpa()));
            itemImage.setImageResource(Utils.getImageResourceId(student.getGender()));
            itemView.setOnClickListener(l -> listener.onItemClick(student));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }
}
