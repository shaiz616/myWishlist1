package com.example.wishmelist.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.User;
import com.example.wishmelist.CreateNewEventFragment;
import com.example.wishmelist.EventListDisplayFragment;
import com.example.wishmelist.Landing_Fragments.LoginFragment;
import com.example.wishmelist.R;
import com.example.wishmelist.newEventFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static String uid;

    private EventDetails event;
    private User u;

    private LandingActivity landing ;

    private TextView welcomeTxtV;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth myAuth;
    FirebaseDatabase db;
    DatabaseReference myDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uid = getIntent().getStringExtra("uid");

        myAuth = FirebaseAuth.getInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, new EventListDisplayFragment()).commit();
        u = landing.getUser();
        if(u == null) {
            u = new User();
        }
        printLogFunc("main", "in onCreate: uid = "+ uid );
        /*
        TODO: need to check if user is null
         */
        welcomeTxtV = findViewById(R.id.welcomeTxt);
        welcomeTxtV.setText("welcome " + u.getName());


        Button btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }

    public String getUid() {
        return uid;
    }
    public void logOut(){
        myAuth.signOut();
        Toast.makeText(this, "success Log Out.",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);


    }
    public void createNewEvent(View view) {
        System.out.println("create new event");
//        CreateNewEventFragment newEvent = new CreateNewEventFragment();
        switchFragment(new CreateNewEventFragment());

    }

    public void setEvent(EventDetails event) {
        this.event = event;
    }

    public void openAddGiftInstance2EventFrag() {
        System.out.println("add a gift");

    }

    public void switchFragment(Fragment newFragment) {

        System.out.println("in main, line 63    **** open the fragment " + newFragment.toString());

//        System.out.println(event.getAddress() +  "\n in main");

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, newFragment).commit();


    }

    /**
     * Todo:    implamnt methods (when there will be something to retrieve)
     */
    public void printLogFunc(String context, String msg) {
        System.out.println("in " + context+ " activity. \n\t\t" +msg );
    }

    public void retrieveGiftListOfEvent() {}

    public void deleteEvent(String eventID) {
        System.out.println("prepare to delete list \n\t\t" + eventID);
    }
}