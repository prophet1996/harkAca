
package com.accademy.harvin.harvinacademy.model.exam;

import java.util.List;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class QuestionPaper implements Parcelable
{

    @SerializedName("_id")
    @Expose
    public
    @PrimaryKey
            @NonNull
    String id;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("questions")
    @Ignore
    @Expose
    private List<Question> questions = null;

    public final static Creator<QuestionPaper> CREATOR = new Creator<QuestionPaper>() {


        @SuppressWarnings({
            "unchecked"
        })
        public QuestionPaper createFromParcel(Parcel in) {
            QuestionPaper instance = new QuestionPaper();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.questions, (com.accademy.harvin.harvinacademy.model.exam.Question.class.getClassLoader()));
            return instance;
        }

        public QuestionPaper[] newArray(int size) {
            return (new QuestionPaper[size]);
        }

    }
    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(v);
        dest.writeList(questions);
    }

    public int describeContents() {
        return  0;
    }

}
