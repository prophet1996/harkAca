package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.McqActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.exam.Question;
import com.accademy.harvin.harvinacademy.model.exam.QuestionPaper;

import java.util.List;

/**
 * Created by ishank on 17/9/17.
 */

public class QuestionNavigationListAdapter extends RecyclerView.Adapter<QuestionNavigationListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Question> questionList;
    private QuestionPaper questionPaper;
    private String greenColor="#00C851";
    private String yelloColor="#ffbb33";
    private String blueColor="#2196f3";
    private NavigationQuestionClickedListener navigationQuestionClickedListener;
    public interface NavigationQuestionClickedListener{
        void onNavigationClicked(int position);
    }
    public void setNavigationQuestionClickedListener(NavigationQuestionClickedListener navigationQuestionClickedListener){
        this.navigationQuestionClickedListener=navigationQuestionClickedListener;
    }

//    public QuestionNavigationListAdapter(Context context, List<Question> questionList) {
//        this.inflater = LayoutInflater.from(context);
//        this.questionList=questionList;
//    }
    public QuestionNavigationListAdapter(Context context, QuestionPaper questionPaper) {
        this.inflater = LayoutInflater.from(context);
        this.questionPaper=questionPaper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.layout_mcq_navigation_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.navQuestionNumber.setText(Integer.toString(position + 1));
        holder.navQuestionNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationQuestionClickedListener.onNavigationClicked(position);
            }
        });
        if(questionPaper.getQuestions().get(position).isClicked)
        holder.indicatorView.setBackgroundColor(Color.parseColor(greenColor));
        else
            holder.indicatorView.setBackgroundColor(Color.parseColor(blueColor));

    }

    @Override
    public int getItemCount() {
        return questionPaper.getQuestions().size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         TextView navQuestionNumber;
         View indicatorView;


        public ViewHolder(View itemView) {
            super(itemView);
            navQuestionNumber=itemView.findViewById(R.id.mcq_nav_question_number);
            indicatorView=itemView.findViewById(R.id.indicator_navigation_list);




        }
    }
}
