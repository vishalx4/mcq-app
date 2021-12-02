package com.example.finalassignment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;
import com.example.finalassignment.R;

import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListViewHolder> {

    List<Question> questionList;
    QuestionCardListener listener;

    public interface QuestionCardListener{
        void onClickCard(Question question);
    }

    public QuestionListAdapter(List<Question> questionList,Context context){
        this.questionList = questionList;
        listener = (QuestionCardListener) context;
    }

    @NonNull
    @Override
    public QuestionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.question_card,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListViewHolder holder, int position) {

        holder.bind(questionList.get(position));

        holder.itemView.setOnClickListener(v->{
            listener.onClickCard(questionList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
