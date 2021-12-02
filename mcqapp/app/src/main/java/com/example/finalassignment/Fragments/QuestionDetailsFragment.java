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
import com.example.finalassignment.Models.Options;
import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;
import com.example.finalassignment.R;
import com.example.finalassignment.ViewModel.DataViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class QuestionDetailsFragment extends Fragment {

    Question question ;
    TextView que_tv,op1,op2,op3,op4;
    MaterialCardView mop1,mop2,mop3,mop4;
    DataViewModel dataViewModel;
    Button prev_btn,next_btn;
    NextPrevQuestion listener;
    SwitchMaterial bookmark_switch;
    public static final int Blue =0xFF2196F3 ,Green=0xFF03DAC5;



    // interface for moving to previous and next Question
    public interface NextPrevQuestion{
        void nextPrevQue(int position);
    }
    public QuestionDetailsFragment() { }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (NextPrevQuestion) context;
    }



    public static QuestionDetailsFragment newInstance(Question question)
    {
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("QUE", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getParcelable("QUE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_details, container, false);

        que_tv = view.findViewById(R.id.question);
        op1 = view.findViewById(R.id.op1);
        op2 = view.findViewById(R.id.op2);
        op3 = view.findViewById(R.id.op3);
        op4 = view.findViewById(R.id.op4);
        mop1 = view.findViewById(R.id.card_op1);
        mop2 = view.findViewById(R.id.card_op2);
        mop3 = view.findViewById(R.id.card_op3);
        mop4 = view.findViewById(R.id.card_op4);

        prev_btn = view.findViewById(R.id.prev_que);
        next_btn = view.findViewById(R.id.next_que);

        bookmark_switch = view.findViewById(R.id.bookmark_switch);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelProvider viewModelProvider = new ViewModelProvider(getActivity());
        dataViewModel = viewModelProvider.get(DataViewModel.class);


        /*
            if no next and previous question is present then it's view must not be present
         */

        if(question.getId() == 0 ){
            prev_btn.setVisibility(View.GONE);
        }

        if(question.getId() == 9){
            next_btn.setVisibility(View.GONE);
        }

        // setting option into views
        que_tv.setText(question.getQuestion());
        Options options = question.getOptions();
        op1.setText(options.getOp1());
        op2.setText(options.getOp2());
        op3.setText(options.getOp3());
        op4.setText(options.getOp4());



        // if bookmark is already set then set that bookmark
        if(question.getBookmark()){
            bookmark_switch.setChecked(true);
        }



        // if option is already chosen then set that option
        String option = question.getSelectedOption();
        if( !option.equals("0") )
        {
            setOption(option);
        }



        Log.d(
                "pathak",
                question.getQuestion()+" "+dataViewModel.getQuestionList()
                        .getValue()
                        .get(question.getId())
                .getQuestion()
        );

        // option click listeners
        mop1.setOnClickListener(v->{
            setOption("1");
        });

        mop2.setOnClickListener(v->{
            setOption("2");
        });

        mop3.setOnClickListener(v->{
            setOption("3");
        });

        mop4.setOnClickListener(v->{
            setOption("4");
        });


        // moving between questions next and previous
        prev_btn.setOnClickListener(v->{
            listener.nextPrevQue(question.getId()-1);
        });

        next_btn.setOnClickListener(v->{
            listener.nextPrevQue(question.getId()+1);
        });


        // listening to bookmark switch
        bookmark_switch.setOnCheckedChangeListener((buttonView,isChecked)->{
                int id = question.getId();
                if(isChecked){
                    question.setBookmark(isChecked);
                    dataViewModel
                            .getQuestionList()
                            .getValue()
                            .get(question.getId())
                            .setBookmark(isChecked);
                }
                else{
                    question.setBookmark(isChecked);
                    dataViewModel
                            .getQuestionList()
                            .getValue()
                            .get(question.getId())
                            .setBookmark(isChecked);
                }
        });


    }


    // on options chose we must change the color of that option
    private void setOption(String op)
    {
        switch (op)
        {
            case "1":
                dataViewModel.getQuestionList()
                        .getValue()
                        .get(question.getId())
                        .setSelectedOption(op);

                mop1.setCardBackgroundColor(Green);
                mop2.setCardBackgroundColor(Blue);
                mop3.setCardBackgroundColor(Blue);
                mop4.setCardBackgroundColor(Blue);
                break;
            case "2":
                dataViewModel.getQuestionList()
                        .getValue()
                        .get(question.getId())
                        .setSelectedOption(op);

                mop1.setCardBackgroundColor(Blue);
                mop2.setCardBackgroundColor(Green);
                mop3.setCardBackgroundColor(Blue);
                mop4.setCardBackgroundColor(Blue);
                break;
            case "3":
                dataViewModel.getQuestionList()
                        .getValue()
                        .get(question.getId())
                        .setSelectedOption(op);

                mop1.setCardBackgroundColor(Blue);
                mop2.setCardBackgroundColor(Blue);
                mop3.setCardBackgroundColor(Green);
                mop4.setCardBackgroundColor(Blue);
                break;
            case "4":
                dataViewModel.getQuestionList()
                        .getValue()
                        .get(question.getId())
                        .setSelectedOption(op);

                mop1.setCardBackgroundColor(Blue);
                mop2.setCardBackgroundColor(Blue);
                mop3.setCardBackgroundColor(Blue);
                mop4.setCardBackgroundColor(Green);
                break;
        }
    }


}