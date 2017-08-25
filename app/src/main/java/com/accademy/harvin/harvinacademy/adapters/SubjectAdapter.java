package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.CourseActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.Subject;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;
import static com.accademy.harvin.harvinacademy.utils.Constants.SUBJECT_KEY;

/**
 * Created by ishank on 24/6/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {


     PopupMenu popup;

    private Subject mSubject;
    private Gson  GSON= new Gson();
    private Context context;


    public SubjectAdapter(Subject mSubject, Context context) {
        Log.d("adapter", "done");

        this.mSubject=mSubject;
        this.context = context;
    }


    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SubjectAdapter.ViewHolder holder, final int position) {


        holder.id.setText(""+(position+1));
        holder.desc.setText(mSubject.getChapters().get(position).getChapterDescription());
        holder.chapter_name.setText(mSubject.getChapters().get(position).getChapterName());
        holder.chapter.setText("Chapter");
        holder.chapter.setOnClickListener(gotchapteractivityListener(position));
        holder.chapter_name.setOnClickListener(gotchapteractivityListener(position));
        holder.id.setOnClickListener(gotchapteractivityListener(position));

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

    private View.OnClickListener gotchapteractivityListener(final int position) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs=context.getSharedPreferences(SUBJECT_KEY,MODE_PRIVATE);
                SharedPreferences.Editor mPrefsEditor=mPrefs.edit();
                mPrefsEditor.putString(SUBJECT_KEY,GSON.toJson(mSubject.getChapters().get(position)));
                mPrefsEditor.commit();
                Intent i = new Intent(context,CourseActivity.class);
                //i.putExtra(SUBJECT_KEY,tab_pos);
                //i.putExtra(CHAPTER_KEY,chapter_list.getChapter_name());
                context.startActivity(i);
            }
        };
    }

    @Override
    public int getItemCount() {
        return mSubject.getChapters().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView chapter_name;
        public TextView desc;
        public TextView chapter;//remains the same
        public TextView buttonViewOption;
        public ViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.chapter_no);
            chapter_name=(TextView)itemView.findViewById(R.id.chapter_title);
            desc=(TextView)itemView.findViewById(R.id.chapter_description);
            chapter=(TextView)itemView.findViewById(R.id.chapter);
            buttonViewOption=(TextView)itemView.findViewById(R.id.textViewOptions);



        }
    }
}
