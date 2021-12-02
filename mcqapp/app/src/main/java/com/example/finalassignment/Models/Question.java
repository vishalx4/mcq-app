package com.example.finalassignment.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Question implements Parcelable
{
    private int id;
    private String question,correct_option,selectedOption;
    private Options options;
    private Boolean bookmark;

    public Question(int id, String question, String correct_option, String selectedOption, Options options, Boolean bookmark) {
        this.id = id;
        this.question = question;
        this.correct_option = correct_option;
        this.selectedOption = selectedOption;
        this.options = options;
        this.bookmark = bookmark;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public Boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(String correct_option) {
        this.correct_option = correct_option;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }


    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        correct_option = in.readString();
        selectedOption = in.readString();
        options = in.readParcelable(Options.class.getClassLoader());
        byte tmpBookmark = in.readByte();
        bookmark = tmpBookmark == 0 ? null : tmpBookmark == 1;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(correct_option);
        dest.writeString(selectedOption);
        dest.writeParcelable(options, flags);
        dest.writeByte((byte) (bookmark == null ? 0 : bookmark ? 1 : 2));
    }
}
