package com.example.wishmelist.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
//    EventListDisplayFragment eventDisplay;

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
        String itemID = giftItemArrayList.get(position).getId();
        boolean permission = MainActivity.isUserRegister;
        holder.txtView.setText("item name: " + giftItemArrayList.get(position).getItemName() + "\nitem model : " + giftItemArrayList.get(position).getItemModel());

        holder.shareIV.setOnClickListener(view -> context.copyLinkToClipboard(itemID));

        if(!permission) {
            holder.deleteItem.setVisibility(View.GONE);
            holder.editItem.setVisibility(view.GONE);
        } else {

            holder.deleteItem.setOnClickListener(view -> context.ask2ConfirmDeleteItem(itemID));

            holder.editItem.setOnClickListener(view -> context.editItemFunc(itemID));

        }

    }


    public int getItemCount() {
        return giftItemArrayList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView txtView;
        CardView cardView;
        ImageView deleteItem, editItem, shareIV;
        EventListDisplayFragment eventDisplay;

        public MyViewHolder2(@NonNull View itemView/*, Fragment frag*/) {
            super(itemView);
            txtView = itemView.findViewById(R.id.eventNameField);
            deleteItem = view.findViewById(R.id.deleteBTN);
            editItem = view.findViewById(R.id.editBTN);
            shareIV = view.findViewById(R.id.imgBTNshare);
            cardView = view.findViewById(R.id.recycleCardView);
//            eventDisplay = (EventListDisplayFragment) frag;
        }
    }
}
