package com.accademy.harvin.harvinacademy.fragment;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.accademy.harvin.harvinacademy.R;
import com.accademy.harvin.harvinacademy.adapters.SubjectAdapter;
import com.accademy.harvin.harvinacademy.model.Chapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {
    private static int SUBJECT_ID[] = {0, 1, 2, 3};
    private static int subject;

    String chape;
    private ArrayList<Chapter> list;
    //recyclerview objects
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public StudyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("done", "done");
        View v = inflater.inflate(R.layout.fragment_study, container, false);

        Bundle bundle = getArguments();
        subject = bundle.getInt("key");
        Log.d("done1", "" + subject);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(container.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setPadding(10,10,10,10);
        loadRecyclerViewItem(subject);

        adapter = new SubjectAdapter(list, container.getContext());



        recyclerView.setAdapter(adapter);



        return v;
    }


    private void loadRecyclerViewItem(int pos) {
        //you can fetch the data from server or some apis
        //for this tutorial I am adding some dummy data directly
        for (int i = 1; i <= 14; i++) {
            Chapter myList = new Chapter(i,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi molestie nisi dui.",
                    "Dummy Heading " + pos
            );
            list.add(myList);

        }
        Log.d("done","done"+list.get(1));

    }
}