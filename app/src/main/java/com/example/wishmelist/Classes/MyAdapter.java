package com.example.wishmelist.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.EventListDisplayFragment;
import com.example.wishmelist.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] value;
    EventListDisplayFragment context;
    ArrayList<EventDetails> eventlists;

    View view;
//    MainActivity main;
    EventListDisplayFragment eventDisplay;



    public MyAdapter(EventListDisplayFragment contx, ArrayList<EventDetails> event) {
        context = contx;
        eventlists = event;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context.getContext());
        view = inflater.inflate(R.layout.rec_view_row, parent, false);

//        main = (MainActivity)  parent.getContext();

//        eventDisplay =(EventListDisplayFragment) getContext();
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.txtView.setText(value[position]);
        holder.txtView.setText("Event name: " + eventlists.get(position).getEventName()+"\nevent ID : "+ eventlists.get(position).getEventID());
        holder.deleteEvent.setOnClickListener(view -> {
            context.deleteEventFunc(eventlists.get(position).getEventID()   );
        });
        holder.editEvent.setOnClickListener(view ->{
            System.out.println("your in MyAdapter no"+ position);
            context.editEventFunc(eventlists.get(position).getEventID());
        });

        holder.cardView.setOnClickListener(v -> context.go2WishList(eventlists.get(position).getEventID()));

    }

    @Override
    public int getItemCount() {

        return eventlists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtView;
        CardView cardView;
        ImageView deleteEvent, editEvent;
        EventListDisplayFragment eventDisplay;

        public MyViewHolder(@NonNull View itemView/*, Fragment frag*/) {
            super(itemView);
            txtView = itemView.findViewById(R.id.recViewTarget);
            deleteEvent = view.findViewById(R.id.deleteEventBTN);
            editEvent = view.findViewById(R.id.editEventBTN);
            cardView = view.findViewById(R.id.recycleCardView);
//            eventDisplay = (EventListDisplayFragment) frag;
        }
    }
}
