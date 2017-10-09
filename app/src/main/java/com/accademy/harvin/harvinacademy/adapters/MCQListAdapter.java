package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.MCQListActivity;
import com.accademy.harvin.harvinacademy.McqActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.exam.Exam;

import java.util.List;

import static com.accademy.harvin.harvinacademy.utils.Constants.EXAM_ID_KEY;

/**
 * Created by ishank on 8/10/17.
 */

public class MCQListAdapter extends RecyclerView.Adapter<MCQListAdapter.ViewHolder> {
    public List<Exam> examList;
    private Context context;

    public MCQListAdapter(List<Exam> examList, Context context){
        this.examList=examList;
        this.context = context;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.examType.setText(examList.get(position).getExamType().toString());
        holder.examNumber.setText((position+1)+"");
        holder.examMaxMarks.setText(examList.get(position).getMaximumMarks()+"");
        holder.examName.setText(examList.get(position).getExamName());
        holder.examCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, McqActivity.class);
                i.putExtra(EXAM_ID_KEY,examList.get(position).getId());
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
         private TextView examNumber;
         private TextView examName;
         private TextView examType;
         private TextView examMaxMarks;
         private RelativeLayout examCard;

         public ViewHolder(View itemView) {
             super(itemView);
             examNumber=itemView.findViewById(R.id.exam_no);
             examType=itemView.findViewById(R.id.exam_type);
             examName=itemView.findViewById(R.id.exam_name);
             examMaxMarks=itemView.findViewById(R.id.max_marks);
             examCard=itemView.findViewById(R.id.exam_card_layout);
         }
     }
}
