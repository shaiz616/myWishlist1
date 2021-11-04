package com.example.wishmelist;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.GiftItem;
import com.example.wishmelist.Classes.MyAdapter;
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
import java.util.Collection;

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

    private String uid, eventId;
    private ArrayList<GiftItem> itemArrayList;


    MainActivity main;
    RecyclerView recView;
    MyAdapter2 adapter;
    ImageButton btn;
    User user;

    FirebaseDatabase db;
    DatabaseReference myDbRef;
    FirebaseAuth myAuth;

    public DisplayWishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayWishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayWishFragment newInstance(String param1, String param2) {
        DisplayWishFragment fragment = new DisplayWishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemArrayList = new ArrayList<GiftItem>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list_display, container, false);
        main = (MainActivity) getActivity();
//        uid = main.getUid();
        eventId = main.getEvent().getEventID();
        db = FirebaseDatabase.getInstance();
        myDbRef = db.getReference("event-list/"+eventId);
        recView = view.findViewById(R.id.recyclerView);
        myAuth.getCurrentUser();
        btn = view.findViewById(R.id.deleteEventBTN);
        getData(view);
        return view;
    }


    public void getData(View view) {
        boolean IsUserAnonymous = myAuth.getCurrentUser().isAnonymous();
        boolean IsUserRegular = myAuth.getCurrentUser().getEmail().isEmpty();
        myDbRef.child("wish-list").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("read data");

                for (DataSnapshot snap : snapshot.getChildren()) {

                    System.out.println("key: " + snap.getKey() +"\n link :" +snap.child("link") );
                   GiftItem item = new GiftItem();
                    item.setId(snap.getKey());
                    System.out.println("itemID1 = " + snap.getKey());
                    System.out.println("itemID2 = " + snap.child("link").getValue().toString());


                    item.setItemName(snap.child("name").getValue().toString());
                    item.setLink(snap.child("link").getValue().toString());
//                    item.setItemPrice(snap.child("price").getValue().toString());
                    // item.setId(snap.child("id").getValue().toString());
//                    item.setItemModel(snap.child("model").getValue().toString());
//                    System.out.println("in display l140, event name = " + snap.child("eventName").getValue().toString());

/*                    EventDetails.EventDate eDate = new EventDetails.EventDate(
                            Integer.parseInt(snap.child("eventDate/dayOfMonth").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/month").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/year").getValue().toString())
                    );*/

//                    event.setEventDate(eDate);
                    itemArrayList.add(item);
                }
                displayData(itemArrayList);
                main.printLogFunc("display frag, l151", itemArrayList.size() + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("failed to read value", error.toException());
            }
        });
    }



        public void displayData(ArrayList data){
            MyAdapter2 adapter = new MyAdapter2(this, data);
            recView.setAdapter(adapter);
            recView.setLayoutManager(new LinearLayoutManager(main));
        }

        public void deleteItemFunc (String itemId){
            System.out.println("prepare to delete Item" + itemId);
            myDbRef.child("wish-list").child(itemId).removeValue();


        }

        public void editEventFunc (String eventId,int position){

            System.out.println("prepare to edit event" + eventId);
            main.switchFragment(new AddGift2EventGiftlistFragment());

        }


        public void checkResult (String value){
            eventId = value;
            System.out.println("in display frag l176\n\t\t value = " + value);
        }

    }
