package com.example.wishmelist.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        String eventId = eventlists.get(position).getEventID();
        holder.txtView.setText("Event name: " + eventlists.get(position).getEventName()+"\nevent ID : "+ eventId);
        holder.deleteEvent.setOnClickListener(view -> {
            context.deleteEventFunc(eventId);
        });

        holder.editEvent.setOnClickListener(view ->{
            System.out.println("your in MyAdapter no"+ position);
            context.editEventFunc(eventId);
        });

        holder.shareIV.setOnClickListener(view -> {
//            Toast.makeText(context.getContext(),"ID = " + eventId, Toast.LENGTH_LONG).show();
            System.out.println("ID = " + eventId);
            context.shareEvent(eventId);
        });

        holder.cardView.setOnClickListener(v -> context.go2WishList(eventId));

    }

    @Override
    public int getItemCount() {

        return eventlists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtView;
        CardView cardView;
        ImageView deleteEvent, editEvent, shareIV;
        EventListDisplayFragment eventDisplay;

        public MyViewHolder(@NonNull View itemView/*, Fragment frag*/) {
            super(itemView);
            txtView = itemView.findViewById(R.id.eventNameField);
            deleteEvent = view.findViewById(R.id.deleteBTN);
            editEvent = view.findViewById(R.id.editBTN);
            shareIV = view.findViewById(R.id.imgBTNshare);
            cardView = view.findViewById(R.id.recycleCardView);
//            eventDisplay = (EventListDisplayFragment) frag;
        }
    }
}
