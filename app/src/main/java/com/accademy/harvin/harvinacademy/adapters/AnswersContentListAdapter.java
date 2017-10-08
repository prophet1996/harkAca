package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.db.AppDatabase;

import java.util.List;

/**
 * Created by ishank on 23/9/17.
 */

public class AnswersContentListAdapter extends RecyclerView.Adapter<AnswersContentListAdapter.ViewHolder> {

    private List<String> answersList;
    private LayoutInflater layoutInflater;
    private AppDatabase appDatabase;
    private boolean stateClicked=false;
    private String questionId;
    public AnswersContentListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        appDatabase=AppDatabase.getInMemoryDatabase(context);

    }
public void setAnswersList( List<String> answersList,String questionId){
    this.answersList = answersList;
    this.questionId=questionId;


}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.answers_content_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("options","option="+answersList.get(position));
        holder.answerOptionButton.setText(answersList.get(position));
        holder.answerOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.answerOptionCheckBox.isChecked())
                {     holder.answerOptionCheckBox.setChecked(false);
                    stateClicked=false;
                save(stateClicked);}
                else{
                    holder.answerOptionCheckBox.setChecked(true);
                save(stateClicked);

                }
            }
        });



    }
    private void save(boolean stateClicked){
        appDatabase.questionModel().updateQuestionClicked(stateClicked,questionId);
    }

    @Override
    public int getItemCount() {
        Log.d("options","size="+answersList.size());

        return answersList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        Button answerOptionButton;
        final CheckBox answerOptionCheckBox ;
        ViewHolder(View itemView) {
            super(itemView);
            answerOptionButton=(Button)itemView.findViewById(R.id.answer_option_textview);
             answerOptionCheckBox=(CheckBox) itemView.findViewById(R.id.answer_option_checkbox);



        }
    }
}
