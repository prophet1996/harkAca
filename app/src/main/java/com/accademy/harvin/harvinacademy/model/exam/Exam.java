
package com.accademy.harvin.harvinacademy.model.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("examName")
    @Expose
    private String examName;
    @SerializedName("examDate")
    @Expose
    private String examDate;
    @SerializedName("examType")
    @Expose
    private String examType;
    @SerializedName("maximumMarks")
    @Expose
    private Integer maximumMarks;
    @SerializedName("positiveMarks")
    @Expose
    private Integer positiveMarks;
    @SerializedName("negativeMarks")
    @Expose
    private Integer negativeMarks;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("questionPaper")
    @Expose
    private String questionPaper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Integer getMaximumMarks() {
        return maximumMarks;
    }

    public void setMaximumMarks(Integer maximumMarks) {
        this.maximumMarks = maximumMarks;
    }

    public Integer getPositiveMarks() {
        return positiveMarks;
    }

    public void setPositiveMarks(Integer positiveMarks) {
        this.positiveMarks = positiveMarks;
    }

    public Integer getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(Integer negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getQuestionPaper() {
        return questionPaper;
    }

    public void setQuestionPaper(String questionPaper) {
        this.questionPaper = questionPaper;
    }

}
