package com.example.dialog_toolbar.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dialog_toolbar.R;
import com.example.dialog_toolbar.controller.SearchController;
import com.example.dialog_toolbar.controller.SearchControllerImp;
import com.example.dialog_toolbar.controller.SortController;
import com.example.dialog_toolbar.controller.SortControllerImp;
import com.example.dialog_toolbar.model.Student;

import java.util.List;
import java.util.Objects;


public class ListStudentFragment extends Fragment {
    private RecyclerView recyclerStudent;
    private StudentAdapter mAdapter;
    private StudentViewModel mViewModel;
    private final SearchController mSearchController;
    private final SortController mSortController;

    private int sortNameCounter;
    private int sortIdCounter;
    private int sortGpaCounter;

    private Toolbar toolbar;

    public ListStudentFragment() {
        mSearchController = new SearchControllerImp();
        mSortController = new SortControllerImp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater
                .from(requireContext())
                .inflate(R.layout.fragment_student_list, container, false);
        addRecyclerView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addDivider();
        setupToolbar(view);

    }

    private void setupToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar_student);
        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(this::menuItemClick);
        searchByName();
    }


    private void searchByName() {
        List<Student> students = ListStudentViewModel.getInstance().getStudents().getValue();
        SearchView searchView = (SearchView) toolbar
                .getMenu()
                .findItem(R.id.action_search)
                .getActionView();
        assert searchView != null;
        searchView.setQueryHint(getString(R.string.hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Student> result = mSearchController.findByName(students, newText);
                mAdapter.updateStudent(result);
                return false;
            }
        });
    }


    private boolean menuItemClick(MenuItem item) {
        List<Student> students = ListStudentViewModel.getInstance()
                .getStudents().getValue();
        int id = item.getItemId();

        if (id == R.id.action_sort_name) {
            sortByName(students);
            return true;
        } else if (id == R.id.action_sort_id) {
            sortById(students);
            return true;
        } else if (id == R.id.action_sort_gpa) {
            sortByGpa(students);
            return true;
        } else if (id == R.id.action_more) {
            showPopup(toolbar);
            return true;
        } else {
            return false;
        }
    }

    private void showPopup(Toolbar toolbar) {
        @SuppressLint("RtlHardcoded") PopupMenu popupMenu =
                new PopupMenu(requireContext(), toolbar, Gravity.RIGHT);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.action_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(this::menuItemClick);
    }

    private void sortByGpa(List<Student> students) {
        sortGpaCounter++;
        if (sortGpaCounter % 2 == 1) {
            mSortController.sortByGpaASC(students);
        } else {
            mSortController.sortByGpaDESC(students);
        }
        mAdapter.updateStudent(students);
    }

    private void sortById(List<Student> students) {
        sortIdCounter++;
        if (sortIdCounter % 2 == 1) {
            mSortController.sortByIdASC(students);
        } else {
            mSortController.sortByIdDESC(students);
        }
        mAdapter.updateStudent(students);
    }

    private void sortByName(List<Student> students) {
        sortNameCounter++;
        if (sortNameCounter % 2 == 1)
            mSortController.sortByNameASC(students);
        else
            mSortController.sortByNameDESC(students);
        mAdapter.updateStudent(students);
    }

    private void addRecyclerView(View view) {
        recyclerStudent = view.findViewById(R.id.recycler_student);
        mAdapter = new StudentAdapter(this::onItemClicked);
        ListStudentViewModel viewModel = ListStudentViewModel.getInstance();
        viewModel.getStudents().observe(getViewLifecycleOwner(), mAdapter::updateStudent);
        viewModel.loadData(requireContext());
        recyclerStudent.setHasFixedSize(true);
        recyclerStudent.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerStudent.setAdapter(mAdapter);

    }

    private void onItemClicked(Student student) {
        mViewModel.setSelectedStudent(student);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addDivider() {
        DividerItemDecoration divider = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(requireContext()
                .getDrawable(R.drawable.divider)));
        recyclerStudent.addItemDecoration(divider);
    }
}
