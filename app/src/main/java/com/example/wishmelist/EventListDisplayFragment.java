package com.example.wishmelist;

//import android.content.Intent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
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
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.GiftItem;
import com.example.wishmelist.Classes.MyAdapter;
//import com.example.wishmelist.Classes.User;
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

//    public static final String MIME_TYPE_CONTENT = "vnd.android.cursor.item/vnd.example.contact";
    public boolean isUserRegister;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String uid;
    private ArrayList<EventDetails> userEventList;


    MainActivity main;
    RecyclerView recView;
    MyAdapter adapter;
    ImageButton btn;

    ClipboardManager clipboard;
    ContentResolver myResolver;
    ClipData clip;


    FirebaseDatabase db;
    DatabaseReference myDbRef;
    FirebaseAuth myAuth;
    EditText inputSearchListByID;
    Button btnSearchList;

    public EventListDisplayFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @return A new instance of fragment EventListDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventListDisplayFragment newInstance(String param1, String param2) {
        EventListDisplayFragment fragment = new EventListDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        userEventList = new ArrayList<>();

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

        clipboard = (ClipboardManager) main.getSystemService(main.CLIPBOARD_SERVICE);
        myResolver = main.getContentResolver();


        inputSearchListByID.setOnLongClickListener(v -> {
            pasteEventLinkFromClipBoard();
            return true;
        });

        recView = view.findViewById(R.id.recyclerWishView);
//        adapter = new MyAdapter(this.getActivity(),userEventList);

        db = FirebaseDatabase.getInstance();
        myDbRef = db.getReference("plain-user/" + uid);
        String uMail = myAuth.getCurrentUser().getEmail();


        if ( uMail != null) {
            isUserRegister = true;
            main.createFloatBtn(view, "from event display, l-140", uid, new CreateNewEventFragment());


        } else {
            isUserRegister = false;

        }


        btn = view.findViewById(R.id.deleteBTN);
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


        if(userEventList.size() != 0) {
            System.out.println("in display l-163, event list length = " + userEventList.size());
            userEventList.clear();
        }
        getData(view);
        return view;
    }


    public void pasteEventLinkFromClipBoard() {
        Toast.makeText(this.getActivity(), "prepare to eat paste 2", Toast.LENGTH_LONG).show();
        clip = clipboard.getPrimaryClip();
        ClipData.Item item = clip.getItemAt(0);
        String pasteData = item.getText().toString();
        inputSearchListByID.setText(pasteData);
        Toast.makeText(this.getActivity(), "prepare to eat paste 3\n" + pasteData, Toast.LENGTH_LONG).show();

    }


    public void getData(View view) {

        // fetch data from database
        myDbRef.child("eventIDList").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {


                    EventDetails event = new EventDetails();
                    event.setEventID(snap.getKey());
                    event.setEventName(snap.getValue().toString());
                    System.out.println("eventID1 = " + event.getEventName());

                    userEventList.add(event);
                }

                // if fetch data was success - display the data
                displayData(userEventList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("failed to read value", error.toException());
            }
        });
    }

        //      atcivate adapter to display data returned from DB
    public void displayData(ArrayList data) {
        adapter = new MyAdapter(this, data);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(main));
    }

    public void ask2confirmDeleteEvent(String eventId) {

        String str = "Are you sure?\n press confirm to delete and remove event,\n or cancel to keep it";

        System.out.println("prepare to delete event " + eventId);
        main.popupDialog(this, str, eventId);

    }


    public void deleteEventFunc(String eventId) {
        myDbRef = db.getReference();
        System.out.println("prepare to delete event " + eventId);

//        myDbRef.child("event-list").child(eventId).removeValue();
//        myDbRef.child("plain-user/" + uid + "/eventIDList/" + eventId).removeValue();

    }

    public void shareEvent(String eventId) {
//        Toast.makeText(this.getActivity(),"ID = " + eventId, Toast.LENGTH_LONG).show();

        clip = ClipData.newPlainText("eventID", eventId);
        clipboard.setPrimaryClip(clip);


    }

    public void editEventFunc(String eventId) {

        System.out.println("prepare to edit event" + eventId);

//        myDbRef.child("event-list/" + eventId + "/eventName" ).setValue("my 45th birthDay");
        myDbRef = db.getReference("event-list/" + eventId);
        myDbRef.addValueEventListener(new ValueEventListener(

        ) {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                GiftItem gift = snap.getValue(GiftItem.class);

                EventDetails event = snapshot.getValue(EventDetails.class);

                main.setEvent(event);
                main.switchFragment(new Edit_EventFragment(), eventId, true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void go2WishList(String eventId) {
        System.out.println("prepare to display wishlist of event " + eventId);
        main.switchFragment(new DisplayWishFragment(), eventId, true);
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

                        main.switchFragment(new DisplayWishFragment(), inputValue, true);
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



}

