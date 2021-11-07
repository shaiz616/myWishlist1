package com.example.wishmelist;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.MyAdapter;
import com.example.wishmelist.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListDisplayFragment extends Fragment {

    //    private String[] str = new String[20];
    private String uid, temp;
    private ArrayList<EventDetails> userEventList;


    FloatingActionButton fab;
    MainActivity main;
    RecyclerView recView;
    MyAdapter adapter;
    ImageButton btn;
    User user;

    FirebaseDatabase db;
    DatabaseReference myDbRef;
    FirebaseAuth myAuth;
    EditText inputSearchListByID;
    Button btnSearchList;

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

        myAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list_display, container, false);
        inputSearchListByID = view.findViewById(R.id.editTextTextPersonName2);
        btnSearchList = view.findViewById(R.id.btnSearchList);
        main = (MainActivity) getActivity();
        uid = main.getUid();
//        user = new User();
//        EventDetails event1 = new EventDetails();

//        System.out.println(userEventList);
//        main.printLogFunc("event display frag/76 |||", event1.printEvent());

//        eventOptions = getResources().getStringArray(R.array.event_type_options);


        recView = view.findViewById(R.id.recyclerView);
//        adapter = new MyAdapter(this.getActivity(),userEventList);

        db = FirebaseDatabase.getInstance();
        myDbRef = db.getReference("plain-user/" + uid);


        String uMail = myAuth.getCurrentUser().getEmail();
        System.out.println("l105| umail = " + uMail);

        if (uMail != null) {
            /*
             *dinamically create fab
             * */
            createFloatBtn(view);


        }

        btn = view.findViewById(R.id.deleteEventBTN);
        btnSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputSearchListByID.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill the input",
                            Toast.LENGTH_SHORT).show();
                } else {
                    SearchWishList();
                }
            }
        });
        getData(view);
        return view;
    }


    public void getData(View view) {
//        myDbRef = db.getReference("event-list");
        myDbRef.child("eventIDList").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("read data");
                for (DataSnapshot snap : snapshot.getChildren()) {

                    EventDetails event = new EventDetails();
                    event.setEventID(snap.getKey());
                    event.setEventName(snap.getValue().toString());
                    System.out.println("eventID1 = " + event.getEventName());




/*                    EventDetails.EventDate eDate = new EventDetails.EventDate(
                            Integer.parseInt(snap.child("eventDate/dayOfMonth").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/month").getValue().toString()),
                            Integer.parseInt(snap.child("eventDate/year").getValue().toString())
                    );*/

//                    event.setEventDate(eDate);
                    userEventList.add(event);
                }
                displayData(userEventList);
                main.printLogFunc("display frag, l151", userEventList.size() + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("failed to read value", error.toException());
            }
        });
    }


    public void createFloatBtn(View view) {
        fab = new FloatingActionButton(main);
        fab.setId(View.generateViewId());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.printLogFunc("display frag", "this is FABulous");
                main.createNewEvent(view);
            }
        });

        fab.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_input_add));
//        fab.setImageResource(R.drawable.button_gradient);


        fab.setElevation(2);
        fab.setFocusable(true);
        LinearLayout lin = view.findViewById(R.id.eventListDisplayLayout);

        lin.addView(fab);

    }

    public void displayData(ArrayList data) {
        MyAdapter adapter = new MyAdapter(this, data);
//        main.printLogFunc("display frag/141\n\t\t", this.toString()  + "\n\t\tend");
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(main));
    }
    public void deleteEventFunc(String eventId) {
        System.out.println("prepare to delete event" + eventId);
//        myDbRef.child("event-list").child(eventId).removeValue();


    }

    public void editEventFunc(String eventId) {

        System.out.println("prepare to edit event" + eventId);
//        myDbRef.child("event-list/" + eventId + "/eventName" ).setValue("my 45th birthDay");

//        EventDetails event = new EventDetails();
//        event.setEventID(eventId);
//        main.setEvent(event);
//        main.switchFragment(new DisplayWishFragment(), eventId);

    }

    public void go2WishList(String eventId) {
        System.out.println("prepare to display wishlist of event " + eventId);
        Intent intent = new Intent(getActivity().getBaseContext(),
                MainActivity.class);
        intent.putExtra("objID", eventId);
        getActivity().startActivity(intent);
        main.switchFragment(new Edit_EventFragment(), eventId);


    }

    public void SearchWishList() {
        String inputValue = inputSearchListByID.getText().toString().trim();
        // regex
        String ptrn = "^[a-zA-Z0-9-]*$";
        System.out.println(inputValue + " val input");
        db = FirebaseDatabase.getInstance();
        Pattern p = Pattern.compile(ptrn);
        Matcher m = p.matcher(inputValue);
        if (m.matches()) {
            myDbRef = db.getReference("event-list/" + inputValue);
            myDbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println("in onDataChange");

                    System.out.println("input val before if: " + inputValue);
                    if (snapshot.getValue() != null && inputValue.contains(myDbRef.getKey())) {
                        System.out.println(snapshot.getValue().toString() + "in iff");

                        // switch to fragment of wishlist of the id
                    } else {
                        Toast.makeText(getActivity(), "Invalid Event ID",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("fail input");
                }
            });
        } else {
            Toast.makeText(getActivity(), "Invalid Event ID",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void checkResult(String value) {
        temp = value;
        System.out.println("in display frag l176\n\t\t value = " + value);
    }


}

