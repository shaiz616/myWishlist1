package com.example.wishmelist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishmelist.AddGift2EventGiftlistFragment;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.GiftItem;
import com.example.wishmelist.CreateNewEventFragment;
import com.example.wishmelist.DisplayWishFragment;
import com.example.wishmelist.Edit_EventFragment;
import com.example.wishmelist.EventListDisplayFragment;
import com.example.wishmelist.R;
import com.example.wishmelist.editItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String uid, uMail;
    public static boolean isUserRegister;


    private EventDetails event;

    private LandingActivity landing ;
    FloatingActionButton fab;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth myAuth;
    private FirebaseDatabase db;
    private DatabaseReference dbUserRef, dbEventRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uid = getIntent().getStringExtra("uid");

        landing = new LandingActivity();

        db = FirebaseDatabase.getInstance();
        dbUserRef = db.getReference("plain-user/" + uid );
        dbEventRef = db.getReference("event-list");

        myAuth = FirebaseAuth.getInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, new EventListDisplayFragment()).setReorderingAllowed(true)
                .addToBackStack(null).commit();


        /**
         * check if user is register or anonymous
         *      (if user is anonymous then getEmail() will return null)
         */
        uMail = myAuth.getCurrentUser().getEmail();

        if ( uMail != null) {
            isUserRegister = true;
        } else {
            isUserRegister = false;
        }


        Button btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener(v -> logOut(v));
    }


    public String getUid() {
        return uid;
    }

    public void logOut(View view){

        System.out.println("qaz | bye");
//        landing.setUid(null);
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        myAuth.signOut();
//        landing.keepLoginData("", "", "");
        Toast.makeText(this, "success Log Out.",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }


    public void setEvent(EventDetails event) {
        this.event = event;
    }

    public EventDetails getEvent() {
        return event;
    }


    public void popupDialog(Fragment origin, String message,String objID ){

        System.out.println(origin.toString() + " /777\n " + message);

        AlertDialog.Builder builder = new AlertDialog.Builder(origin.getContext());
        builder.setTitle("Please Confirm");
        builder.setMessage(message);
//        AlertDialog dialog = builder.create();
        builder.setPositiveButton("confirm", (dialog1, which) -> resolveChanges(origin,objID, true));
        builder.setNegativeButton("Cancel", (Dialog, which) -> resolveChanges(origin,objID, false));
        builder.show();

    }

    public void resolveChanges(Fragment origin, String objID, boolean flag) {
        Toast.makeText(this, "object ID = " + objID, Toast.LENGTH_LONG).show();

        System.out.println("flag is " + flag);
        if (flag) {

            if(origin instanceof Edit_EventFragment) {
                ((Edit_EventFragment) origin).retriveEventChanges(objID);
            }else if (origin instanceof editItem) {
                ((editItem) origin).retriveWishChanges();
            }else if (origin instanceof EventListDisplayFragment) {
                ((EventListDisplayFragment) origin).deleteEventFunc(objID); }
            else if (origin instanceof DisplayWishFragment) {
                ((DisplayWishFragment) origin).deleteItemFunc(objID); }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        String message = savedInstanceState.getString("msg");
        builder.setTitle(message );

        return builder.create();
    }

    public void switchFragment(Fragment newFragment, String objId, boolean flag ) {
        System.out.println("in main, l-159      **** switching to " + newFragment.toString() + "\nflag is " + flag);

        fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("objID", objId);
        newFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        if(flag) {
            fragmentTransaction.setReorderingAllowed(true).addToBackStack(null);
        }
        else
            System.out.println("fragment " + newFragment.toString() + " was not added to backstack");
        fragmentTransaction.commit();

    }


/*
    public void switchFragment(Fragment newFragment, String objId) {

        System.out.println("in main, line 170    **** open the fragment " + newFragment.toString());

//        System.out.println(event.getAddress() +  "\n in main");
//        Intent intent = getIntent();
//        String objectID = intent.getStringExtra("objID");

        fragmentTransaction = fragmentManager.beginTransaction().setReorderingAllowed(true)
                .addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("objID", objId);
        newFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainFrame, newFragment).commit();


    }*/

    public void createFloatBtn(View view, String msg, String objId, Fragment nextFragment) {
        fab = new FloatingActionButton(this);
        fab.setId(View.generateViewId());
        fab.setOnClickListener(v -> switchFragment(nextFragment, objId, true));

        fab.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_input_add));

        fab.setElevation(2);
        fab.setFocusable(true);
        LinearLayout lin = view.findViewById(R.id.wishListDisplayLayout);

        lin.addView(fab);

    }



    public void printLogFunc(String context, String msg) {
        System.out.println("in " + context+ " activity. \n\t\t" +msg );
    }

    public void retrieveGiftListOfEvent(String eventId) {
        ArrayList<GiftItem> wishlist = new ArrayList<>();

        dbEventRef.child(eventId + "/wish-list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot snap : snapshot.getChildren() ) {
                    GiftItem gift = snap.getValue(GiftItem.class);
//                    System.out.println("in main retrive wishlist l 163, wish =" + gift.getItemModel());

                    wishlist.add(gift);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println("in main retrive wishlist l 182, finish");

    }

    public void deleteEvent(String eventID) {
        System.out.println("prepare to delete list \n\t\t" + eventID);
    }

    public void popToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void resetFields(ArrayList<EditText> etField) {
        for (EditText et : etField) {
            et.setText("");
        }
    }

}