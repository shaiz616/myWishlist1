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
import android.widget.Button;
import android.widget.ImageButton;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.MyAdapter;
import com.example.wishmelist.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListDisplayFragment extends Fragment {

//    private String[] str = new String[20];
    private String uid, temp;
    private ArrayList<EventDetails> userEventList;



    MainActivity main;
    RecyclerView recView;   MyAdapter adapter;
    ImageButton btn;
    User user;

    FirebaseDatabase db;
    DatabaseReference myDbRef;

    public EventListDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment EventListDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListDisplayFragment newInstance(String param1, String param2) {
        EventListDisplayFragment fragment = new EventListDisplayFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userEventList = new ArrayList<EventDetails>();
//        uid = "asdf";
//        for (int i=0; i < str.length; i++) {
//            str[i] = "something " + i;
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list_display, container, false);
        main = (MainActivity) getActivity();
        uid = main.getUid();
        user = new User();
//        EventDetails event1 = new EventDetails();

//        System.out.println(userEventList);
//        main.printLogFunc("event display frag/76 |||", event1.printEvent());

//        eventOptions = getResources().getStringArray(R.array.event_type_options);

        recView = view.findViewById(R.id.recyclerView);
//        adapter = new MyAdapter(this.getActivity(),userEventList);

        db = FirebaseDatabase.getInstance();
        myDbRef = db.getReference("plain-user/"+ uid);

        btn = view.findViewById(R.id.deleteEventBTN);

        getData(view);
        return view;
    }




    public void getData(View view) {
        myDbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("read data");
                for (DataSnapshot snap : snapshot.child("event-list").getChildren()) {
                    EventDetails event = new EventDetails();
//                    event.setEventType(snap.child("eventType").getValue().toString());
//                    event.setAddress(snap.child("address").getValue().toString());
                    System.out.println("eventID1 = " + snap.getKey());;
                    event.setEventID(snap.getKey());
                    System.out.println("eventID2 = " + event.getEventID());;

/*                    EventDetails.EventDate eDate = new EventDetails.EventDate(
                            Integer.parseInt(snap.child("eventDate/dayOfMonth").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/month").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/year").getValue().toString())
                    );*/

//                    event.setEventDate(eDate);
                    userEventList.add(event);
                }
                displayData(userEventList);
                main.printLogFunc("display frag, l129", userEventList.size() + "" );

            }

             @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("failed to read value", error.toException());
            }
        });
    }

    public void displayData( ArrayList data) {
        MyAdapter adapter = new MyAdapter(this, data);
//        main.printLogFunc("display frag/141\n\t\t", this.toString()  + "\n\t\tend");
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(main));
    }

    public void deleteEventFunc(String eventId) {
        System.out.println("prepare to delete event" + eventId);
        myDbRef.child("event-list").child(eventId).removeValue();


    }

    public void editEventFunc(String eventId, int position) {
        System.out.println("prepare to edit event" + eventId);
        myDbRef.child("event-list/" + eventId + "/event-name" ).setValue("my 45th birthDay");

    }


    public void checkResult(String value) {
        temp = value;
        System.out.println("in display frag l176\n\t\t value = " + value);
    }






}