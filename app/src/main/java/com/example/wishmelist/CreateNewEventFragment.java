package com.example.wishmelist;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.GiftItem;
import com.example.wishmelist.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewEventFragment extends Fragment {

    private String[] eventOptions;
    private String address, eventType, eventDate, eventName, uid, eventId;
//    private boolean flag;
    private User user ;

    Button btnConfirm;
    EditText etAddress, etName;
    Spinner spnr;
    ArrayAdapter<String> spinerAdapt;
    TextView showDatePic;
    DatePickerDialog.OnDateSetListener dateListener;
    EventDetails.EventDate date;


    FirebaseAuth myAuth;
    FirebaseDatabase fireDB;
    DatabaseReference dbUserRef, eventListRef;

    MainActivity main;

    public CreateNewEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CreateNewEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNewEventFragment newInstance(String param1, String param2) {
        CreateNewEventFragment fragment = new CreateNewEventFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_event, container, false);
        main = (MainActivity) getActivity();
        uid = main.getUid();
        user = new User();
//        eventName = "birthday";

        etAddress = view.findViewById(R.id.etAddress);
        etName = view.findViewById(R.id.editTxtEventName);

        // spinner - prop-down menu to select event type ( wedding, birthday, ect)
        spnr = view.findViewById(R.id.spinner);
        myAuth = FirebaseAuth.getInstance();
        fireDB = FirebaseDatabase.getInstance();
        dbUserRef = fireDB.getReference("plain-user/"+uid);

        System.out.println("print userRef: " + dbUserRef.toString());

//        retrieveUserData();
//        System.out.println(user.getPassword());

        eventOptions = getResources().getStringArray(R.array.event_type_options);
        spinerAdapt = new ArrayAdapter<String>( getActivity() , android.R.layout.simple_list_item_1, eventOptions);

        spinerAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr.setAdapter(spinerAdapt);
        spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("event type: " + eventOptions[position]);
                eventType = eventOptions[position];
                System.out.println("type is: " + eventType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("nothing selected");

            }
        });


        // date Picker - to set event date
        showDatePic = view.findViewById(R.id.tvDate);
        showDatePic.setOnClickListener(v -> setDate(view));

        /**
         * Todo: save the date as dd/mm/yy string format,
         *      change to date object ( create new class if need)
         *      check how date is saved in DB and how its returned, then splyced back
         *      to something manageable
         */
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                eventDate = dayOfMonth + "/" + month +"/" + year;
                date = new EventDetails.EventDate(dayOfMonth, month, year);

                System.out.println("create new event| l153\n\t\t" +  eventDate.toString());
            }
        };


        btnConfirm = view.findViewById(R.id.createNewEventBTN);
//        btnConfirm.setOnClickListener(v -> captureEventDetails(view));
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                flag = validateEventDetails(view);
//                System.out.println("line 135)   flag is " + flag);
                if(validateEventDetails(view)) {
                    System.out.println(" line 173/ \tevent name" + eventName);
                    retrieveUserData();
                }
            }
        });
        return view;
    }


    public boolean validateEventDetails(View view) {
        System.out.println("is the input legit?");
        eventName = etName.getText().toString();
        address = etAddress.getText().toString();
        if(eventName == null) {
            popToast("wouldn't you like to have a meaningful name?" +
                    "\n please enter a name for your event");
            return false;
        }
        if(eventDate == null) {
            popToast("can't create new event without date\nplease enter event date");
            return false;
        } else if( eventType.equals("Event type")) {
            System.out.println(eventType);
            popToast("what are you celebrating?\nplease choose event type");
            return false;
        }else if (address.isEmpty() || address.length() < 5) {
            popToast("where to send your gift?\nplease provide an address");
            return false;
        } else {
            System.out.println("success! :) ");
            return true;
        }

//        address = etAddress.getText().toString();
//        eventType = spnr.getText().toString();

//        return true;
    }

    public void setDate(View view) {
        System.out.println("select a date");
        Calendar cal = Calendar.getInstance();
        System.out.println("line 144:" + cal.toString());
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH);
        int dd = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_WithActionBar,
                dateListener,
                yy,mm,dd);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        date = new EventDetails.EventDate(dd, mm, yy);

    }

    public void retrieveUserData() {


//        System.out.println("in create, uid = " + uid);
        dbUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user.setName(snapshot.child("name").getValue().toString());

                System.out.println("aas" + user.getName());
                createNewEvent();
//                saveEvent2DB();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails());
            }

        });

/*        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previosChildName) {
                System.out.println("in create line 246, on child added " + snapshot.getKey() );
            }

//            @Override
//            public void onChildChanged

            @Override
            public void onCancelled(DatabaseError err) {
                System.out.println("create event:onCancelled" + err.toException());
            }
        };*/
    }

    public void createNewEvent()  {

        System.out.println("finish :" + user.getName());

        System.out.println("create new event instance " + date.toString());
//        SimpleDateFormat pattern = new SimpleDateFormat("dd/mm/yy");
//        EventDetails.EventDate date = new EventDetails.EventDate();

        EventDetails newEvent = new EventDetails( eventName, eventType, date, address);

//        user.addEvent(newEvent);
//        System.out.println("uid ="  + FbUser.getUid() + ", name = " + FbUser);
//        User user = new User(FbUser.);

//        myDBRef.child(user.getUid()).child("event-list").setValue(event) ;

        newEvent.printEvent();
        saveEvent2DB(newEvent);
    }

    public void saveEvent2DB(EventDetails event) {
//        dbUserRef = fireDB.getReference("plain-user/"+uid);

        eventListRef = fireDB.getReference("event-list");
        eventListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                eventId = eventListRef.push().getKey();
                event.setEventID(eventId);

//                eventListRef = fireDB.getReference("plain-user/"+uid + "/event-list");

//                uid = myDBRef.child(myAuth.getCurrentUser().getUid().getValue().toString(),)
                eventListRef.child(eventId).setValue(event);

                System.out.println("in create new event, line 303\n" +
                        "************************ \n eventid = " + eventId);
                dbUserRef.child("eventIDList").child(eventId).setValue(event.getEventName());
                main.setEvent(event);
                main.switchFragment(new AddGift2EventGiftlistFragment(), eventId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("***///      error: " + error.toString());

            }
        });

        System.out.println("problam? : " + event.getAddress());

    }

    public void popToast(String msg) {
        Toast.makeText( getActivity(), msg, Toast.LENGTH_LONG ).show();
    }



}