package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalassignment.Adapters.QuestionListAdapter;
import com.example.finalassignment.Fragments.InstructionFragment;
import com.example.finalassignment.Fragments.QuestionDetailsFragment;
import com.example.finalassignment.Fragments.QuestionListFragment;
import com.example.finalassignment.Fragments.Summary;
import com.example.finalassignment.Fragments.TimeFragment;
import com.example.finalassignment.Models.Question;
import com.example.finalassignment.ViewModel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class    Home extends AppCompatActivity implements
        InstructionFragment.StartTest,
        QuestionListAdapter.QuestionCardListener,
        QuestionDetailsFragment.NextPrevQuestion,
        Summary.RestartTestListener
{

    // view model variables
    private ViewModelProvider viewModelProvider;
    private DataViewModel dataViewModel;


    private List<Question> questionList;

    private ProgressDialog progressDialog;
    private AlertDialog alertDialog,submitDialog;

    private MenuItem submit_menu_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("check","onCreate");

        viewModelProvider = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        dataViewModel = viewModelProvider.get(DataViewModel.class);
        questionList = new ArrayList<>();



        if( savedInstanceState == null )
        {
            handleFragment(R.id.time_fragment,new TimeFragment());
            handleFragment(R.id.replaceable_fragment,new InstructionFragment());
        }

        handleLiveData();

    }

// observing on live data
    private void handleLiveData() {

        dataViewModel.getQuestionList().observe(this, questions -> {
            questionList = questions;
        });


        dataViewModel.getRequestStatus().observe(this, status ->{
            switch (status)
            {
                case FAILED:
                    showError();
                    break;
                case SUCCEEDED:
                    hideSpinner();
                    break;
                case IN_PROCESS:
                    showSpinner();
                    break;
            }
        });

        dataViewModel.getIsTimerRunning().observe(this,aBoolean -> {
            Log.d("vishal",aBoolean.toString());
            if(!aBoolean){
                submitTest();
            }
        });


    }


    private void showError() {
        hideSpinner();
        if(alertDialog == null){
            alertDialog = getAlertDialog();
        }
        alertDialog.show();
    }
    private AlertDialog getAlertDialog() {
        return new AlertDialog.Builder(this)
                .setTitle("Failed to Fetch question List")
                .setMessage("want to retry")
                .setPositiveButton("Retry", (dialog, which) -> {
                            dialog.dismiss();
                            dataViewModel.reFetchCountries();
                }
                )
                .setNegativeButton("cancel", (dialog,which)->{
                    dialog.dismiss();
                    finish();
                })
                .setCancelable(false)
                .setIcon(R.drawable.alert)
                .create();
    }
    private AlertDialog getSubmitDialog(){
        return  new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to Submit the test?")
                .setPositiveButton("Yes", (dialog, which) -> {
                            dialog.dismiss();
                            submitTest();
                        }
                )
                .setNegativeButton("No", (dialog,which)->{
                    dialog.dismiss();
                })
                .setCancelable(false)
                .create();
    }
    private void hideSpinner() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    private void showSpinner() {

        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setTitle("getting questons ready ...");
            progressDialog.setMessage("loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }



    // this method starts the test by setting timer on
    @Override
    public void onClickStartTestBtn() {

        if(dataViewModel.getRequestStatus().getValue() == DataViewModel.RequestStatus.SUCCEEDED)
        {
            dataViewModel.setSubmitButtonVisibility(true);
            submit_menu_btn.setVisible(true);
            dataViewModel.startTimer();
            handleFragment(R.id.replaceable_fragment,new QuestionListFragment());
        }
        else {
            Toast.makeText(this,R.string.load_question_failed,Toast.LENGTH_SHORT).show();
        }
    }

    /*
        by clicking on the card we are moving to the question detail screen
        so , if we press back then we must go back to question list screen
        that's why questionLoaded view_model is used check onBackPressed code
     */
    @Override
    public void onClickCard(Question question)
    {
        dataViewModel.setQuestionsLoaded(true);
        handleFragment(R.id.replaceable_fragment,QuestionDetailsFragment.newInstance(question));
    }


    /*
        go to the next or previous question
     */
    @Override
    public void nextPrevQue(int position) {

        Question question ;
        if(position >=0 && position<=9){
            if(dataViewModel.getQuestionList().getValue()!=null){
                question= dataViewModel.getQuestionList().getValue().get(position);
                handleFragment(R.id.replaceable_fragment,QuestionDetailsFragment.newInstance(question));
            }
        }

    }


    /*
        if we are on question detail screen then we must go back to Question List screen
        else exit the app
     */
    @Override
    public void onBackPressed() {

        if(dataViewModel.getQuestionsLoaded()){
            dataViewModel.setQuestionsLoaded(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.replaceable_fragment, new QuestionListFragment()).commit();
        }
        else {
            super.onBackPressed();
        }

    }


    /*
        set visibility on submit button on only when we start the test
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit_menu,menu);
        submit_menu_btn = menu.getItem(0);
        submit_menu_btn.setVisible(dataViewModel.getSubmitButtonVisibility());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        submitDialog = getSubmitDialog();
        submitDialog.show();
        return true;
    }

    // on press submit button
    private void submitTest() {
        dataViewModel.setSubmitButtonVisibility(false);
        submit_menu_btn.setVisible(false);
        dataViewModel.stopTimer();
        handleFragment(R.id.replaceable_fragment,new Summary());
    }


    // on press restart button of summary screen
    @Override
    public void restartTest() {
        dataViewModel.setSubmitButtonVisibility(true);
        dataViewModel.restartTest();
        submit_menu_btn.setVisible(true);
        handleFragment(R.id.replaceable_fragment,new QuestionListFragment());
    }


    // on press exit button on summary screen
    @Override
    public void exitApp() {
        finish();
    }


    private void handleFragment(int id,Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(id,fragment)
                .commit();
    }
}