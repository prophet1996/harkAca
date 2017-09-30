package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.exam.Question;

import java.util.List;

/**
 * Created by ishank on 17/9/17.
 */

public class QuestionNavigationListAdapter extends RecyclerView.Adapter<QuestionNavigationListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Question> questionList;

    public QuestionNavigationListAdapter(Context context, List<Question> questionList) {
        this.inflater = LayoutInflater.from(context);
        this.questionList=questionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.layout_mcq_navigation_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.navQuestionNumber.setText(Integer.toString(position + 1));

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
     class ViewHolder extends RecyclerView.ViewHolder{
         TextView navQuestionNumber;
         CheckBox boxNotAttempted;
         CheckBox boxRevision;
         CheckBox boxCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            navQuestionNumber=(TextView)itemView.findViewById(R.id.mcq_nav_question_number);
            boxNotAttempted=(CheckBox)itemView.findViewById(R.id.check_not_attempted);
            boxCompleted=(CheckBox)itemView.findViewById(R.id.check_completed);
                    boxRevision=(CheckBox)itemView.findViewById(R.id.check_revision);




        }
    }
}
