package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Subject;

import java.util.List;

/**
 * Created by ishank on 24/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
    }

    private Context context;

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        Subject list=subjectList.get(position);
        holder.id.setText(""+list.getSub_id());
        holder.desc.setText(list.getSub_desc());
        holder.sub.setText(list.getSub_name());
        //Add button code here


    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView sub;
        public TextView desc;
        public TextView chapter;//remains the same
        public ViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.chapter_no);
            sub=(TextView)itemView.findViewById(R.id.chapter_title);
            desc=(TextView)itemView.findViewById(R.id.chapter_no);
            chapter=(TextView)itemView.findViewById(R.id.chapter);



        }
    }
}
