package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.accademy.harvin.harvinacademy.R;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        DrawerItemHolder drawerHolder;
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            drawerHolder= new DrawerItemHolder();
            view=inflater.inflate(R.layout.custom_drawer_item,parent,false);
            drawerHolder.icon=(ImageView)view.findViewById(R.id.drawer_icon);
            drawerHolder.ItemName=(TextView) view.findViewById(R.id.drawer_itemName);
            view.setTag(drawerHolder);
        }
        else {
            drawerHolder=(DrawerItemHolder) view.getTag();
        }
        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                dItem.getImgResId()));
        drawerHolder.ItemName.setText(dItem.getItemName());

        return view;
    }

    @Override
    public int getCount() {
        return drawerItemList.size();
    }
    private class DrawerItemHolder{
        ImageView icon;
        TextView ItemName;

    }
}
