package com.example.finalassignment.Models;

public class SelectedOption {

    int question_no,option_no,bookmark;

    public SelectedOption(int question_no, int option_no, int bookmark) {
        this.question_no = question_no;
        this.option_no = option_no;
        this.bookmark = bookmark;
    }

    public int getQuestion_no() {
        return question_no;
    }

    public void setQuestion_no(int question_no) {
        this.question_no = question_no;
    }

    public int getOption_no() {
        return option_no;
    }

    public void setOption_no(int option_no) {
        this.option_no = option_no;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }
}
