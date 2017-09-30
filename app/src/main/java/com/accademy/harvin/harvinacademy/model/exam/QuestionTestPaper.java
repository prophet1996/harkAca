
package com.accademy.harvin.harvinacademy.model.exam;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionTestPaper implements Parcelable
{

    @SerializedName("questionPaper")
    @Expose
    private QuestionPaper questionPaper;
    public final static Creator<QuestionTestPaper> CREATOR = new Creator<QuestionTestPaper>() {


        @SuppressWarnings({
            "unchecked"
        })
        public QuestionTestPaper createFromParcel(Parcel in) {
            QuestionTestPaper instance = new QuestionTestPaper();
            instance.questionPaper = ((QuestionPaper) in.readValue((QuestionPaper.class.getClassLoader())));
            return instance;
        }

        public QuestionTestPaper[] newArray(int size) {
            return (new QuestionTestPaper[size]);
        }

    }
    ;

    public QuestionPaper getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(QuestionPaper questionPaper) {
        this.questionPaper = questionPaper;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(questionPaper);
    }

    public int describeContents() {
        return  0;
    }

}
