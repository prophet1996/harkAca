package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.McqActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.exam.Question;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishank on 23/9/17.
 */

public class QuestionContentListAdapter extends RecyclerView.Adapter<QuestionContentListAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Question> questionList;
    private LinearLayoutManager linearLayoutManager;
    private QuestionPaper questionPaper;
    private MarkerForReviewListener markerForReviewListener;
    public interface MarkerForReviewListener{
        void onMarked(int position,boolean checked);
    }
    public void setMarkedForReviewListener(MarkerForReviewListener markerForReviewListener){
        this.markerForReviewListener=markerForReviewListener;
    }

    public QuestionContentListAdapter(Context context, QuestionPaper questionPaper) {
        this.layoutInflater=LayoutInflater.from(context);
        this.questionPaper=questionPaper;
        linearLayoutManager= new LinearLayoutManager(context);
        Log.d("content mcq","adapter createdd");
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.mcq_question_content_list,parent,false);
        Log.d("content mcq","oncreate view createdd");

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Question question=questionPaper.getQuestions().get(position);
        holder.questionNumberTextView.setText("Q"+Integer.toString(position+1));
        question.givenAnswer=new ArrayList<>();
        holder.questionTextView.setText(question.question);
        holder.checkedTextView1.setText(question.getOptions().get(0));
        holder.checkedTextView2.setText(question.getOptions().get(1));
        holder.checkedTextView3.setText(question.getOptions().get(2));
        holder.checkedTextView4.setText(question.getOptions().get(3));
        holder.checkedTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.checkedTextView1.isChecked()){
                holder.checkedTextView1.setChecked(true);
                    questionSelectedListener.onQuestionClicked(position,0);
                    question.givenAnswer.add(holder.checkedTextView1.getText().toString());

                }
                else{questionSelectedListener.onQuestionUnClicked(position,0);
                    holder.checkedTextView1.setChecked(false);
                    try{
                question.answers.remove(holder.checkedTextView1.getText().toString());}
                    catch (Exception ne){
                        ne.printStackTrace();
                    }
                }
                Log.d("listener","clicked"+position);
            }
        });
        holder.checkedTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.checkedTextView2.isChecked()){
                    holder.checkedTextView2.setChecked(true);
                    questionSelectedListener.onQuestionClicked(position,1);
                    question.givenAnswer.add(holder.checkedTextView2.getText().toString());
                }
                else{questionSelectedListener.onQuestionUnClicked(position,1);
                    holder.checkedTextView2.setChecked(false);
                    try{
                        question.answers.remove(holder.checkedTextView2.getText().toString());}
                    catch (Exception ne){
                        ne.printStackTrace();
                    }
                }
                Log.d("listener","clicked"+position);
            }
        });    holder.checkedTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.checkedTextView3.isChecked()){
                    holder.checkedTextView3.setChecked(true);
                    questionSelectedListener.onQuestionClicked(position,2);
                    question.givenAnswer.add(holder.checkedTextView3.getText().toString());
                }
                else{                    questionSelectedListener.onQuestionUnClicked(position,2);
                    holder.checkedTextView3.setChecked(false);
                    try{
                        question.answers.remove(holder.checkedTextView3.getText().toString());}
                    catch (Exception ne){
                        ne.printStackTrace();
                    }
                }
                Log.d("listener","clicked"+position);
            }
        });    holder.checkedTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!holder.checkedTextView4.isChecked()){
                    holder.checkedTextView4.setChecked(true);
                    questionSelectedListener.onQuestionClicked(position,3);
                    question.givenAnswer.add(holder.checkedTextView4.getText().toString());
                }
                else{questionSelectedListener.onQuestionUnClicked(position,3);
                    holder.checkedTextView4.setChecked(false);}
                Log.d("listener","clicked"+position);
            }
        });

holder.marckedCheckBox.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  if(!holder.marckedCheckBox.isChecked()) {markerForReviewListener.onMarked(position,false);}
                                                  else {markerForReviewListener.onMarked(position, true);}Log.d("listener","clicked"+position);
                                              }});
    }

    @Override
    public int getItemCount() {
        return questionPaper.getQuestions().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
         private TextView questionTextView;
         private TextView questionNumberTextView;
         //private RecyclerView answersRecyclerView;
        private AppCompatCheckedTextView checkedTextView1;
        private AppCompatCheckedTextView checkedTextView2;
        private AppCompatCheckedTextView checkedTextView3;
        private AppCompatCheckedTextView checkedTextView4;
        private AppCompatCheckBox marckedCheckBox;
         ViewHolder(View itemView) {
            super(itemView);
            questionTextView=itemView.findViewById(R.id.question_textview_content);
            questionNumberTextView=itemView.findViewById(R.id.question_number_textview_content);
            checkedTextView1=itemView.findViewById(R.id.answer_one);
            checkedTextView2=itemView.findViewById(R.id.answer_two);
            checkedTextView3=itemView.findViewById(R.id.answer_three);
            checkedTextView4=itemView.findViewById(R.id.answer_four);
             marckedCheckBox=itemView.findViewById(R.id.marked_checked);

        }

    }
    public interface QuestionSelectedListener{
        void  onQuestionClicked(int position,int answerPosition);
        void onQuestionUnClicked(int position,int answerPosition);
    }

    private  QuestionSelectedListener questionSelectedListener;
    public void setQuestionSelectedListener(QuestionSelectedListener questionSelectedListener) {
        this.questionSelectedListener = questionSelectedListener;
    }
}
