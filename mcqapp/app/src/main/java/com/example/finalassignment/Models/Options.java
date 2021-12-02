package com.example.finalassignment.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Options implements Parcelable {
    String op1,op2,op3,op4;

    public Options(String op1, String op2, String op3, String op4) {
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;
    }

    public Options() {

    }

    protected Options(Parcel in) {
        op1 = in.readString();
        op2 = in.readString();
        op3 = in.readString();
        op4 = in.readString();
    }

    public static final Creator<Options> CREATOR = new Creator<Options>() {
        @Override
        public Options createFromParcel(Parcel in) {
            return new Options(in);
        }

        @Override
        public Options[] newArray(int size) {
            return new Options[size];
        }
    };

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getOp4() {
        return op4;
    }

    public void setOp4(String op4) {
        this.op4 = op4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(op1);
        dest.writeString(op2);
        dest.writeString(op3);
        dest.writeString(op4);
    }
}
