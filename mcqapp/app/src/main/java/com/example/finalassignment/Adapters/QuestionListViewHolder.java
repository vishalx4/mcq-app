package com.example.finalassignment.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;
import com.example.finalassignment.R;

public class QuestionListViewHolder extends RecyclerView.ViewHolder {

    TextView question_tv;
    ImageView answered_unanswered_image,bookmark_image;

    public QuestionListViewHolder(@NonNull View itemView) {
        super(itemView);
        question_tv = itemView.findViewById(R.id.question);
        answered_unanswered_image = itemView.findViewById(R.id.answered_unanswered);
        bookmark_image = itemView.findViewById(R.id.bookmark_image);
    }

    public void bind(Question question){

        question_tv.setText(question.getQuestion());

        if(!question.getSelectedOption().equals("0")) {
            answered_unanswered_image.setImageResource(R.drawable.check);
        }
        else {
            answered_unanswered_image.setImageResource(R.drawable.loading);
        }

        if(question.getBookmark()){
            bookmark_image.setVisibility(View.VISIBLE);
        }
        else bookmark_image.setVisibility(View.GONE);


    }

}
