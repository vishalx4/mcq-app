package com.example.finalassignment.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalassignment.R;
import com.example.finalassignment.ViewModel.DataViewModel;

public class TimeFragment extends Fragment {

    DataViewModel viewModel;
    TextView time_value_tv;

    public TimeFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        time_value_tv = view.findViewById(R.id.time_value_tv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(getActivity()).get(DataViewModel.class);


        // observing time
        viewModel.getTime().observe(getActivity(), s ->{
            time_value_tv.setText(s);
        });
    }

}