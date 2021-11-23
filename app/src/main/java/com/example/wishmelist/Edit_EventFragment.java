package com.example.wishmelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_EventFragment extends Fragment {

    String eventId, eventName, eventAddress;
    EditText etName, etAddress;
    TextView popupTEXT;
    Button btn, btnConfirm, btnCancel;

    EventDetails event;
    MainActivity main;

    FirebaseDatabase db;
    DatabaseReference dbEventRef;


    public Edit_EventFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static Edit_EventFragment newInstance() {
        Edit_EventFragment fragment = new Edit_EventFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventId = getArguments().getString("objID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit__event, container, false);

        main = (MainActivity) getActivity();
//        event = new EventDetails();
        eventId = main.getEvent().getEventID();

        db = FirebaseDatabase.getInstance();
        dbEventRef = db.getReference();


        etName = view.findViewById(R.id.editEvent_etName);
        etAddress = view.findViewById(R.id.editEvent_etAddress);

        String str = "have you done all the changes you wished for the event?\n" +
                "Select confirm to finish,\n" +
                "Or cancel to make further changes";
        btn = view.findViewById(R.id.btnConfirmEditEvent);
        System.out.println("edit event79 eventid = " + eventId);
        btn.setOnClickListener(v -> {
            main.popupDialog( this,str, eventId);
        });

        btn = view.findViewById(R.id.btnConfirm);
//        btn.setOnClickListener(v -> )

        return view;
    }

    public void retriveEventChanges(String eventId){

        event = main.getEvent();
        System.out.println("event id = " +eventId);
        String temp = etName.getText().toString();
        eventName = (temp.isEmpty()) ? event.getEventName() : temp;
        event.setEventName(eventName);

        temp = etAddress.getText().toString();
        eventAddress = temp.isEmpty() ? main.getEvent().getAddress() : temp;
        event.setAddress(eventAddress);

//        event = new EventDetails(eventId, eventName);
//        event.setEventName(eventName);
//        event.setAddress(eventAddress);
//        event.setEventID(main.getEvent().getEventID());
//        event.setEventDate(main.getEvent().getEventDate());
//        event.setEventType(main.getEvent().getEventType());
        main.setEvent(event);

        saveChanges2DB(event);
        main.popToast("prepare to edit event 107");

    }


    public void saveChanges2DB(EventDetails event) {

        dbEventRef.child("plain-user/" + main.getUid() + "/eventIDList/" + event.getEventID()).setValue(event.getEventName());
        dbEventRef.child( "event-list/"+ event.getEventID() + "/address").setValue(event.getAddress());
        dbEventRef.child( "event-list/"+ event.getEventID() + "/eventName").setValue(event.getEventName());
        dbEventRef.child( "event-list/"+ event.getEventID() + "/eventDate").setValue(event.getEventDate());
        dbEventRef.child( "event-list/"+ event.getEventID() + "/eventType").setValue(event.getEventType());

    }


}