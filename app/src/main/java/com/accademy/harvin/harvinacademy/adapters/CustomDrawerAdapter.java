package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.AssignmentActivity;
import com.accademy.harvin.harvinacademy.ClassRoomActivity;
import com.accademy.harvin.harvinacademy.MCQListActivity;
import com.accademy.harvin.harvinacademy.McqActivity;
import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.ReportActivity;
import com.accademy.harvin.harvinacademy.ScrollingActivity12;
import com.accademy.harvin.harvinacademy.UserProfile;
import com.accademy.harvin.harvinacademy.model.DrawerItem;

import java.util.List;

/**
 * Created by ishank on 5/7/17.
 */

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem>{
    List<DrawerItem> drawerItemList;
    Context context;
    int layoutResourceId;

    public CustomDrawerAdapter(@NonNull Context context, @LayoutRes int layoutResourceId,@NonNull List<DrawerItem> drawerItemList) {
        super(context, layoutResourceId, drawerItemList);
        this.context=context;
        this.drawerItemList=drawerItemList;
        this.layoutResourceId=layoutResourceId;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DrawerItemHolder drawerHolder;
        View view = convertView;
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            drawerHolder= new DrawerItemHolder();
            view=inflater.inflate(R.layout.custom_drawer_item,parent,false);
            drawerHolder.icon=view.findViewById(R.id.drawer_icon);
            drawerHolder.ItemName=view.findViewById(R.id.drawer_itemName);
            drawerHolder.itemLayout=view.findViewById(R.id.itemLayout);
            view.setTag(drawerHolder);

        }else {
        drawerHolder=(DrawerItemHolder)view.getTag();
        }
        DrawerItem dItem =  this.drawerItemList.get(position);
        Log.d("adapter drawer","position"+position);
        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResId()));
        drawerHolder.ItemName.setText(dItem.getItemName());
        Log.d("adapter drawer","Name"+dItem.getItemName());
        drawerHolder.itemLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:

                        Intent i=new Intent(context,MCQListActivity.class);
                        context.startActivity(i);

                        break;
                    case 1:
                        i=new Intent(context,AssignmentActivity.class);
                        context.startActivity(i);



                        break;
                    case 2:

                        i=new Intent(context,ReportActivity.class);
                        context.startActivity(i);


                        break;
                    case 3:

                        i=new Intent(context,UserProfile.class);
                        context.startActivity(i);
                        break;

                    case 4:
                        i=new Intent(context,ClassRoomActivity.class);
                        context.startActivity(i);
                        break;




                }
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        Log.d("adapter drawer",drawerItemList.size()+"");
        return drawerItemList.size();

    }
    private class DrawerItemHolder{
        ImageView icon;
        TextView ItemName;

        RelativeLayout itemLayout;

    }
}
