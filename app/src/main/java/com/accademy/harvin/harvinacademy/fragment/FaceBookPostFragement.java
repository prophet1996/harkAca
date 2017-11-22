package com.accademy.harvin.harvinacademy.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.FacebookPostAdapter;
import com.accademy.harvin.harvinacademy.model.facebookpost.FacebookPosts;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaceBookPostFragement extends Fragment {
    private RecyclerView  mRecyclerView;
    final static FacebookPosts fb1[]=new FacebookPosts[1];
    private LinearLayoutManager mLinearLayoutManager;
    private FacebookPostAdapter mFaceBookPostAdapter;
    private static Context mContext;
    private static String AccessToken="1054614874673150|BDc9cPag0-B4AKYVdxedXS1ch5M";

    public FaceBookPostFragement() {
        // Required empty public constructor
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_face_book_post_fragement, container, false);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.facebookpost_horizontal_recyclerview);
       mLinearLayoutManager= new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,true);
       final  Handler hadler=new Handler();
         Thread th= new Thread(new Runnable() {
            @Override
            public void run() {

               try {
                   getFacebookPosts();
                   while(fb1[0]==null){}
                   hadler.post(new Runnable() {
                       @Override
                       public void run() {
                           setPost();
                       }
                   });

               }
               catch (NullPointerException ne){
                   ne.printStackTrace();
               }
               catch (IllegalStateException ise){ise.printStackTrace();}
            }
        });
        th.start();
        if(fb1[0]!=null){
       setPost();}
        else {
v.setBackground(mContext.getDrawable(R.drawable.oops));
         //   v.setLayoutParams(new CardView.LayoutParams(FrameLayout.LayoutParams.));
        }
        return v;
    }
   public void setPost(){
        mFaceBookPostAdapter=new FacebookPostAdapter(fb1[0],mContext);

        mRecyclerView.setAdapter(mFaceBookPostAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.scrollToPosition(mFaceBookPostAdapter.getItemCount()-1);
    }
    public static FaceBookPostFragement getInstance(Context mContext){
        FaceBookPostFragement.mContext=mContext;
        return new FaceBookPostFragement();
    }

    public static FacebookPosts getFacebookPosts(){



        new GraphRequest(
                com.facebook.AccessToken.getCurrentAccessToken(),
                "/harvinacademy/posts/?fields=message,attachments&access_token="+AccessToken,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {

                        Gson gson= new Gson();

                          FacebookPosts fb= gson.fromJson(response.getRawResponse(),FacebookPosts.class);
                            fb1[0]=fb;

                        try {
                            Log.d("facebook",fb.getData().get(0).getAttachments().getData().get(0).getMedia().getImage().getSrc());
                        } catch (Exception e) {
                            Log.d("facebook","null exp");
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        return fb1[0];
    }

}
