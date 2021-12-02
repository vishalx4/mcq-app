package com.example.finalassignment.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.finalassignment.R;

public class InstructionFragment extends Fragment {

    // start test button to start the test
    Button start_test_btn;

    private StartTest listener;
    public interface StartTest{
        void onClickStartTestBtn();
    }

    public InstructionFragment() {}

    // to get context of activity to initialize interface listener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (StartTest) context;
    }

    // inflating instruction fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_instruction, container, false);
        start_test_btn = view.findViewById(R.id.start_test_btn);
        return view ;
    }


    // after view has created we can access view from this method

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start_test_btn.setOnClickListener( v->{
            listener.onClickStartTestBtn();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}