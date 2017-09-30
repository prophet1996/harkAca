package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.exam.Question;

import java.util.List;

/**
 * Created by ishank on 23/9/17.
 */

public class QuestionContentListAdapter extends RecyclerView.Adapter<QuestionContentListAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Question> questionList;
    private LinearLayoutManager linearLayoutManager;
    private AnswersContentListAdapter answersContentListAdapter;
    public QuestionContentListAdapter(Context context, List<Question> questionList) {
        this.layoutInflater=LayoutInflater.from(context);
        this.questionList=questionList;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.questionNumberTextView.setText("Q"+Integer.toString(position+1));
        holder.questionTextView.setText(questionList.get(position).getQuestion());
        answersContentListAdapter.setAnswersList(questionList.get(position).getOptions()
        );


    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
         private TextView questionTextView;
         private TextView questionNumberTextView;
         private RecyclerView answersRecyclerView;
         ViewHolder(View itemView) {
            super(itemView);
            questionTextView=(TextView)itemView.findViewById(R.id.question_textview_content);
            questionNumberTextView=(TextView)itemView.findViewById(R.id.question_number_textview_content);
            answersRecyclerView =(RecyclerView)itemView.findViewById(R.id.answers_recyclerView_content);
             answersRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
             answersContentListAdapter= new AnswersContentListAdapter(itemView.getContext());

             answersRecyclerView.setAdapter(answersContentListAdapter);

        }

    }
}
