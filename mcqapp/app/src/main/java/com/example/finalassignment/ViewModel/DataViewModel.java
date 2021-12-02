package com.example.finalassignment.ViewModel;
import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalassignment.Models.Options;
import com.example.finalassignment.Models.Question;
import com.example.finalassignment.Models.SelectedOption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DataViewModel extends AndroidViewModel {

    // time & instruction fragment data
    private MutableLiveData<String> time = new MutableLiveData<>("Timer");
    private CountDownTimer countDownTimer;
    private String totalTimeTaken;
    private Boolean questionsLoaded = false;
    MutableLiveData<Boolean> isTimerRunning = new MutableLiveData<>();



    //questions list
    private MutableLiveData<List<Question>> questionList = new MutableLiveData<>();
    private String API_URL = "https://raw.githubusercontent.com/tVishal96/sample-english-mcqs/master/db.json";
    private RequestQueue queue;





    //request status of fetch list
    private MutableLiveData<RequestStatus> requestStatus = new MutableLiveData<>();






    // submit button visibility
    private boolean submitButtonVisibility = false;

    public boolean getSubmitButtonVisibility() {
        return submitButtonVisibility;
    }

    public void setSubmitButtonVisibility(boolean submitButtonVisibility) {
        this.submitButtonVisibility = submitButtonVisibility;
    }




    // constructor
    public DataViewModel(@NonNull Application application) {
        super(application);
        queue = Volley.newRequestQueue(application);
        fetchQuestionList();
    }









    // methods for timer
    public MutableLiveData<String> getTime(){
        return time;
    }

    public void setTime(String time_value){
        if(time != null) {
            time.setValue(time_value);
        }
    }

    public void startTimer()
    {
        long duration = TimeUnit.MINUTES.toMillis(5);

        countDownTimer = new CountDownTimer(duration,1000){

            @Override
            public void onTick(long millisUntilFinished)
            {

                String time_value = String.format(

                        Locale.ENGLISH,

                        "%02d : %02d",

                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),

                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );

                String total_time_taken = String.format(
                        Locale.ENGLISH,
                        "%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(300000-millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(300000-millisUntilFinished)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(300000-millisUntilFinished))
                );


                setTotalTimeTaken(total_time_taken);
                isTimerRunning.setValue(true);
                setTime(time_value);
            }

            @Override
            public void onFinish()
            {
                isTimerRunning.setValue(false);
            }
        }.start();
    }


    public void stopTimer()
    {
        if(countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    public MutableLiveData<Boolean> getIsTimerRunning() {
        return isTimerRunning;
    }

    public String getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(String totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }








    // methods for question list
    public MutableLiveData<List<Question>> getQuestionList() {
        return questionList;
    }


    public void fetchQuestionList(){
        requestStatus.postValue(RequestStatus.IN_PROCESS);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_URL, null,
                response -> {
                    requestStatus.postValue(RequestStatus.SUCCEEDED);
                    handleRequest(response);
                },
                error -> {
                    Log.d("vishal",error.getMessage().toString());
                    requestStatus.setValue(RequestStatus.FAILED);
                }
        );
        queue.add(jsonObjectRequest);
    }

    // parsing json response into Java Object
    private void handleRequest(JSONObject response)
    {
        List<Question> qList = new ArrayList<>();

        try
        {
            JSONArray questionArray = new JSONArray(response.getString("questions"));

            for(int i=0;i<questionArray.length();i++)
            {
                JSONObject questionObject = new JSONObject(questionArray.get(i).toString());

                JSONArray op = questionObject.getJSONArray("options");
                Options options = new Options   (
                        op.getString(0),
                        op.getString(1),
                        op.getString(2),
                        op.getString(3)
                );
                Question question = new Question(
                        Integer.parseInt(questionObject.getString("id")),
                        questionObject.getString("question"),
                        questionObject.getString("correct_option"),
                        "0",
                        options,
                        false
                );
                Log.d("question_debug",question.toString());

                qList.add(question);
            }

            Collections.shuffle(qList);

            for(int i=0;i<10;i++){
                qList.get(i).setId(i);
            }

            questionList.postValue(qList);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.d("vishal",e.toString());
            requestStatus.postValue(RequestStatus.FAILED);
        }
    }

    public void reFetchCountries(){
        fetchQuestionList();
    }



    // Request status

    public enum RequestStatus
    {
        IN_PROCESS,
        FAILED,
        SUCCEEDED
    }

    public MutableLiveData<RequestStatus> getRequestStatus() {
        return requestStatus;
    }







    /*
       this is for , when user press back button from instruction screen then app must be exit
       but if user press back button when QuestionDetail Fragment is open
       then it must go to the QuestionList Screen not exit the app so that's why this methods are used
       check onBackPress method in Home Screen
    */

    public Boolean getQuestionsLoaded() {
        return questionsLoaded;
    }

    public void setQuestionsLoaded(Boolean questionsLoaded) {
        this.questionsLoaded = questionsLoaded;
    }


    public void restartTest(){
        List<Question> qList = questionList.getValue();

        for(int i=0;i<10;i++)
        {
            qList.get(i).setSelectedOption("0");
            qList.get(i).setBookmark(false);
        }
        Collections.shuffle(qList);
        for(int i=0;i<10;i++){
            qList.get(i).setId(i);
        }
        questionList.setValue(qList);
        isTimerRunning.setValue(true);
        startTimer();
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        questionList.getValue().clear();
        questionsLoaded = false;

        requestStatus = null;
        countDownTimer = null;

        isTimerRunning.setValue(false);


    }
}
