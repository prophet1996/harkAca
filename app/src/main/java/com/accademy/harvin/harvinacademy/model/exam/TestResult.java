package com.accademy.harvin.harvinacademy.model.exam;

/**
 * Created by ishank on 5/11/17.
 */

public class TestResult {
    int nQuestions;
    String nQuestionsAnswered;

    int totalMarks;

    public TestResult(int nQuestions, String nQuestionsAnswered,  int totalMarks) {
        this.nQuestions = nQuestions;
        this.nQuestionsAnswered = nQuestionsAnswered;


        this.totalMarks = totalMarks;
    }
}
