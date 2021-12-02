package com.example.finalassignment.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;
import com.example.finalassignment.R;
import com.example.finalassignment.ViewModel.DataViewModel;

import java.util.List;

public class Summary extends Fragment {

    TextView marks_tv,total_time_taken_tv;
    List<Question> questionList;
    Button restartTest,exitBtn;
    RestartTestListener listener;

    public Summary() { }

    public interface RestartTestListener{
        void restartTest();
        void exitApp();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (RestartTestListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_summary, container, false);
        marks_tv = view.findViewById(R.id.marks);
        restartTest = view.findViewById(R.id.restart_test_btn);
        exitBtn = view.findViewById(R.id.exit_btn);
        total_time_taken_tv = view.findViewById(R.id.total_time);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataViewModel dataViewModel = new ViewModelProvider(getActivity()).get(DataViewModel.class);

        questionList = dataViewModel.getQuestionList().getValue();
        int marks = 0;



        // calculating marks by matching with correct option
        for(int i=0;i<10;i++){

            Question question=null;
            if(questionList != null){
                question = questionList.get(i);
            }

            Log.d("vishal",
                    question.getQuestion()+" "+
                            question.getSelectedOption()+" "+
                            question.getCorrect_option()
                    );

            if(
                    !question.getSelectedOption().equals("0") &&
                            question.getCorrect_option().equals(
                                    String.valueOf(Integer.parseInt(question.getSelectedOption())-1)
                            )
            ){
                marks++;
            }

            marks_tv.setText("total marks : "+marks +" / 10");
            total_time_taken_tv.setText("total time taken : "+dataViewModel.getTotalTimeTaken());


            restartTest.setOnClickListener(v->{
                listener.restartTest();
            });

            exitBtn.setOnClickListener(v->{
               listener.exitApp();
            });

        }




    }
}