
package com.accademy.harvin.harvinacademy.model.exam;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("options")
    @Expose
    private List<String> options = null;
    public final static Creator<Question> CREATOR = new Creator<Question>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Question createFromParcel(Parcel in) {
            Question instance = new Question();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.question = ((String) in.readValue((String.class.getClassLoader())));
            instance.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.answers, (String.class.getClassLoader()));
            in.readList(instance.options, (String.class.getClassLoader()));
            return instance;
        }

        public Question[] newArray(int size) {
            return (new Question[size]);
        }

    }
    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(question);
        dest.writeValue(v);
        dest.writeList(answers);
        dest.writeList(options);
    }

    public int describeContents() {
        return  0;
    }

}
