package com.example.wishmelist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.GiftItem;
import com.example.wishmelist.Classes.MyAdapter2;
import com.example.wishmelist.Classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayWishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayWishFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private String mParam1;
    private String mParam2;

    private String eventId;
    private ArrayList<GiftItem> itemArrayList;


    MainActivity main;
    RecyclerView recWishView;
    MyAdapter2 adapter;
    ImageButton btn;


    ClipboardManager clipboard;
    ContentResolver myResolver;
    ClipData clip;

    FirebaseDatabase db;
    DatabaseReference myDbRef;
    FirebaseAuth myAuth;

    public DisplayWishFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DisplayWishFragment newInstance(String param1, String param2) {
        DisplayWishFragment fragment = new DisplayWishFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemArrayList = new ArrayList<>();


        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_wish, container, false);
        main = (MainActivity) getActivity();
        eventId = getArguments().getString("objID");
        db = FirebaseDatabase.getInstance();
        myDbRef = db.getReference("event-list/" + eventId);
        recWishView = view.findViewById(R.id.recyclerWishView);
        myDbRef = db.getReference("event-list/" + eventId);

        clipboard = (ClipboardManager) main.getSystemService(main.CLIPBOARD_SERVICE);


        String uMail = myAuth.getCurrentUser().getEmail();


        /**
         *dynamically create fab
         * */
        if (uMail != null) {
            main.createFloatBtn(view, "from display wish frag, l-14", eventId, new AddGift2EventGiftlistFragment());
        }

        if (itemArrayList.size() != 0) {
            itemArrayList.clear();
        }
        btn = view.findViewById(R.id.deleteBTN);
//        btn.setOnClickListener(v -> main.popupDialog(this, str, item));
        getWishData(view);
        return view;
    }


    public void getWishData(View view) {
        myDbRef.child("wish-list").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("read data");

                for (DataSnapshot snap : snapshot.getChildren()) {

                    System.out.println("key: " + snap.getKey() + "\n link :" + snap.child("link"));

                    GiftItem wish = snap.getValue(GiftItem.class);
                    wish.setId(snap.getKey());

/*                    GiftItem item = new GiftItem();
                    item.setId(snap.getKey());
                    System.out.println("itemID1 = " + snap.getKey());
                    System.out.println("itemID2 = " + snap.child("link").getValue().toString());


                    item.setItemName(snap.child("itemName").getValue().toString());
                    item.setLink(snap.child("link").getValue().toString());
//                    item.setItemPrice(snap.child("price").getValue().toString());
                    // item.setId(snap.child("id").getValue().toString());
                    item.setItemModel(snap.child("itemModel").getValue().toString());*/
                    System.out.println("in display l150, event name = " + wish.getItemName());

/*                    EventDetails.EventDate eDate = new EventDetails.EventDate(
                            Integer.parseInt(snap.child("eventDate/dayOfMonth").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/month").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/year").getValue().toString())
                    );*/

//                    event.setEventDate(eDate);
                    itemArrayList.add(wish);
                }
                main.printLogFunc("display wish frag, l161", itemArrayList.size() + "");
                displayWishData(itemArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("failed to read value", error.toException());
            }
        });
    }

    public void copyLinkToClipboard(String itemID) {
        clip = ClipData.newPlainText("itemID", itemID);
        clipboard.setPrimaryClip(clip);
    }



    public void displayWishData(ArrayList data) {
        adapter = new MyAdapter2(this, data);
        recWishView.setAdapter(adapter);
        System.out.println("123ass ," + this.toString());

        recWishView.setLayoutManager(new LinearLayoutManager(main));
    }



    public void ask2ConfirmDeleteItem(String itemId) {

        String str = "Are you sure?\n press confirm to delete and remove wish,\n or cancel to keep it";

        System.out.println("prepare to delete Item " + itemId);
        main.popupDialog(this, str, itemId);



//        myDbRef.child("wish-list").child(itemId).removeValue();

//        main.switchFragment(new editItem(), eventId);
    }

    public void deleteItemFunc(String itemId) {

        myDbRef.child("wish-list").child(itemId).removeValue();

//        main.switchFragment(new editItem(), eventId);
    }



    public void editItemFunc(String itemId) {

        String path = eventId + " " + itemId;
        System.out.println("prepare to edit event" + eventId);
        main.switchFragment(new editItem(), path, true);

    }


    public void checkResult(String value) {
        eventId = value;
        System.out.println("in display frag l176\n\t\t value = " + value);
    }

}
