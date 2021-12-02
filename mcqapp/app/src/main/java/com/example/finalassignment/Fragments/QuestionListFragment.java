package com.example.finalassignment.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalassignment.Adapters.QuestionListAdapter;
import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;
import com.example.finalassignment.R;
import com.example.finalassignment.ViewModel.DataViewModel;

import java.util.ArrayList;
import java.util.List;


public class QuestionListFragment extends Fragment {

    RecyclerView recyclerView;
    QuestionListAdapter adapter;
    ViewModelProvider viewModelProvider;
    DataViewModel dataViewModel;


    public QuestionListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelProvider = new ViewModelProvider(getActivity());
        dataViewModel = viewModelProvider.get(DataViewModel.class);

        adapter = new QuestionListAdapter(dataViewModel.getQuestionList().getValue(),getActivity());
        recyclerView.setAdapter(adapter);

    }
}