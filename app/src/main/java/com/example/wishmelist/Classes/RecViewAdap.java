package com.example.wishmelist.Classes;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishmelist.R;

import java.util.ArrayList;

public class RecViewAdap extends RecyclerView.Adapter<RecViewAdap.MyRecViewAdap> {

    ArrayList<EventDetails> eventList;
    Context contxt;
    
    public RecViewAdap(Context cont, ArrayList<EventDetails> list) {
        this.eventList = list;
        this.contxt = cont;
    }

    @NonNull
    @Override
    public MyRecViewAdap onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(contxt);
        System.out.println("in handler " + parent.getContext());
        View view = LayoutInflater.from(contxt).inflate(androidx.recyclerview.R.layout.custom_dialog , parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecViewAdap holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyRecViewAdap extends RecyclerView.ViewHolder {


        public MyRecViewAdap(@NonNull View itemView) {
            super(itemView);
        }
    }
}
