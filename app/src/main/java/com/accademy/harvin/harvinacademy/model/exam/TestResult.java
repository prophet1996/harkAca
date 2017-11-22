package com.accademy.harvin.harvinacademy.model.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ishank on 5/11/17.
 */

public class TestResult {

    private int nQuestions;
    @Expose
    @SerializedName("nQuestionsAnswered")
    String nQuestionsAnswered;
    @Expose
    @SerializedName("mTotal")
    int totalMarks;

    public int getnQuestions() {
        return nQuestions;
    }

    public void setnQuestions(int nQuestions) {
        this.nQuestions = nQuestions;
    }

    public String getnQuestionsAnswered() {
        return nQuestionsAnswered;
    }

    public void setnQuestionsAnswered(String nQuestionsAnswered) {
        this.nQuestionsAnswered = nQuestionsAnswered;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public TestResult(int nQuestions, String nQuestionsAnswered, int totalMarks) {
        this.nQuestions = nQuestions;
        this.nQuestionsAnswered = nQuestionsAnswered;


        this.totalMarks = totalMarks;
    }
}
