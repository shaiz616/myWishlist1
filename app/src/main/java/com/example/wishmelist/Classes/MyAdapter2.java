package com.example.wishmelist.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.DisplayWishFragment;
import com.example.wishmelist.EventListDisplayFragment;
import com.example.wishmelist.R;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2>{


    DisplayWishFragment context;
    ArrayList<GiftItem> giftItemArrayList;

    View view;
//    MainActivity main;
    EventListDisplayFragment eventDisplay;

    public MyAdapter2(DisplayWishFragment contx, ArrayList<GiftItem> giftItemArrayList) {
        this.context = contx;
        this.giftItemArrayList = giftItemArrayList;
    }


    @NonNull
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context.getContext());
        view = inflater.inflate(R.layout.rec_view_row, parent, false);

//        main = (MainActivity) parent.getContext();

//        eventDisplay =(EventListDisplayFragment) getContext();
        return new MyViewHolder2(view);

    }


    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, int position) {
//        holder.txtView.setText(value[position]);
        holder.txtView.setText("item name: " + giftItemArrayList.get(position).getItemName() + "\nitem link : " + giftItemArrayList.get(position).getLink());
        holder.deleteEvent.setOnClickListener(view -> {
            context.deleteItemFunc(giftItemArrayList.get(position).getId());
        });
        holder.editEvent.setOnClickListener(view -> {
            System.out.println("your in MyAdapter no" + position);
            context.editEventFunc(giftItemArrayList.get(position).getLink(), position);
        });
    }


    public int getItemCount() {
        return giftItemArrayList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView txtView;
        ImageView deleteEvent, editEvent;
        EventListDisplayFragment eventDisplay;

        public MyViewHolder2(@NonNull View itemView/*, Fragment frag*/) {
            super(itemView);
            txtView = itemView.findViewById(R.id.recViewTarget);
            deleteEvent = view.findViewById(R.id.deleteEventBTN);
            editEvent = view.findViewById(R.id.editEventBTN);
//            eventDisplay = (EventListDisplayFragment) frag;
        }
    }
}
