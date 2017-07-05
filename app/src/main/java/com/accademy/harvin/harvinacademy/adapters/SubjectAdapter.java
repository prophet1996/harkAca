package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Chapter;

import java.util.List;

/**
 * Created by ishank on 24/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
     final static String SUBJECT_KEY="subject";
    public final static String CHAPTER_KEY="subject";

     PopupMenu popup;

    private List<Chapter> chapterList;

    public SubjectAdapter(List<Chapter> chapterList, Context context) {
        this.chapterList = chapterList;
        this.context = context;
    }

    private Context context;

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        v.setPadding(10,10,10,10);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SubjectAdapter.ViewHolder holder, int position) {
        final Chapter list= chapterList.get(position);
        final int tab_pos=position;
        holder.id.setText(""+list.getChapter_id());
        holder.desc.setText(list.getChapter_desc());
        holder.sub.setText(list.getChapter_name());
        holder.chapter.setText("Chapter");
        holder.chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,CourseActivity.class);
                i.putExtra(SUBJECT_KEY,tab_pos);
                i.putExtra(CHAPTER_KEY,list.getChapter_name());
                context.startActivity(i);
            }
        });

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inflating menu from xml resource
                popup = new PopupMenu(context, holder.buttonViewOption);
                popup.inflate(R.menu.options_menu);
                popup.dismiss();
                popup.show();


                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                //handle menu1 click
                                break;
                            case R.id.menu2:
                                //handle menu2 click
                                break;
                            case R.id.menu3:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        popup=null;
                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView sub;
        public TextView desc;
        public TextView chapter;//remains the same
        public TextView buttonViewOption;
        public ViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.chapter_no);
            sub=(TextView)itemView.findViewById(R.id.chapter_title);
            desc=(TextView)itemView.findViewById(R.id.chapter_description);
            chapter=(TextView)itemView.findViewById(R.id.chapter);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptions);



        }
    }
}
