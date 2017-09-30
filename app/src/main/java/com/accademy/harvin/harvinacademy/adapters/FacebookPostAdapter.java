package com.accademy.harvin.harvinacademy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.model.facebookpost.Datum;
import com.accademy.harvin.harvinacademy.model.facebookpost.FacebookPosts;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ishank on 1/8/17.
 */

public class FacebookPostAdapter extends RecyclerView.Adapter<FacebookPostAdapter.ViewHolder> {
   private FacebookPosts facebookPosts;
   private Context mContext;
    private LayoutInflater inflater;
    private List<Datum> posts;

    public FacebookPostAdapter(FacebookPosts facebookPosts, Context mContext) {
        this.facebookPosts = facebookPosts;
        this.mContext = mContext;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=  inflater.inflate(R.layout.facebook_post_list,parent,false);
       posts=getData();

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.postDesc.setText(posts.get(position).getAttachments().getData().get(0).getDescription());
        holder.postTitle.setText(posts.get(position).getAttachments().getData().get(0).getTitle());
try {
    Glide
            .with(mContext)
            .load(posts.get(position).getAttachments().getData().get(0).getMedia().getImage().getSrc())
            .placeholder(R.drawable.solidfill)
            .into(holder.postImage);

}catch (Exception e){
    e.printStackTrace();
}
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        
    }
});
    }

    @Override
    public int getItemCount() {
        return facebookPosts.getData().size();
    }

    public List<Datum> getData() {
        return facebookPosts.getReverseData();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        ImageView postImage;
        TextView postTitle;
        TextView postDesc;
         ViewHolder(View itemView) {
            super(itemView);
            postImage=(ImageView)itemView.findViewById(R.id.facebook_post_imageview);
            postTitle=(TextView)itemView.findViewById(R.id.facebook_post_title);
            postDesc=(TextView)itemView.findViewById(R.id.facebook_post_desc);
        }
    }
}
