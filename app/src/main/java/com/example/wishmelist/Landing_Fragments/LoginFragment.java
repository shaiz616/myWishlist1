package com.example.wishmelist.Landing_Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wishmelist.Activities.LandingActivity;
import com.example.wishmelist.Classes.EventDetails;
import com.example.wishmelist.Classes.User;
import com.example.wishmelist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText editEmail, editPassword;
    private TextView tvRegis;

    private String email, password;

    private LandingActivity landing ;
    private FirebaseAuth myAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        myAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser != null) {
            System.out.println(currentUser.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editEmail = view.findViewById(R.id.editTextEmail);
        editPassword = view.findViewById(R.id.editTextPW);
        landing = (LandingActivity) getActivity();

        Button btn = view.findViewById(R.id.loginBtn);
        btn.setOnClickListener(v -> captureInput(view));

/*        btn = view.findViewById(R.id.btnReset);
        btn.setOnClickListener(v -> resetFields(view));*/

        Button btn1 = view.findViewById(R.id.btnGuest);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("hello1");
                LogAnonimously();
            }
        });
//        btn1.setOnClickListener(v -> landing.loginFunctoin());

        return view;
    }
    public void LogAnonimously(){
        myAuth.signInAnonymously().addOnCompleteListener(this.landing, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String s = "";

                    System.out.println("login anonymous successful");
                    FirebaseUser user = myAuth.getCurrentUser();
                    s = user.getUid();
                    System.out.println(s);
                    System.out.println("getting anonymous info user "+ user.getUid());
                    landing.MoveToMainScreen(s);



                }else {
                    // If sign in fails, display a message to the user.
                    System.out.println("signInAnonymously:failure" + task.getException());

                    Toast.makeText(landing, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void captureInput(View view) {

        if (editEmail.getText().toString().isEmpty()) {
            System.out.println("mail is empty");
        }else {
            email = editEmail.getText().toString();
        }

        if(editPassword.getText().toString().isEmpty()) {
            System.out.println("pw is empty");
        } else {
            password = editPassword.getText().toString();
        }

        System.out.println("hello2" + password);

        loginFunc(email, password);

    }

    public void loginFunc(String email, String pw) {
        myAuth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this.landing, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful() ) {
                        System.out.println("login success");
                        FirebaseUser user = myAuth.getCurrentUser();

                        String uid = user.getUid();
                        User u = new User();

                        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
                        DatabaseReference myDBRef = fireDB.getReference("plain-user").child(uid);
                        myDBRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                u.setName(snapshot.child("name").getValue().toString());
                                System.out.println("l161\t\t" + u.getName());
                                landing.setUser(u);
                                landing.MoveToMainScreen(uid);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.w("ERR", "failed to read value");

                            }
                        });
                        System.out.println(u.getName());
                        /*myDBRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User u = dataSnapshot.getValue(User.class);
                                EventDetails event;
                                Object child = dataSnapshot.child("event-list");
                                landing.doSomething("login", "in loginfunc:\n\t\t" +u.getPassword());
//                                System.out.println(" line 160)  " + dataSnapshot.child("event-list").getValue());
                                /*for( DataSnapshot snap : dataSnapshot.child("event-list").getChildren()) {
                                    System.out.println("line 162\n\t" + snap.child("eventDate/month").getValue().toString());
                                    EventDetails.EventDate()
                                    event = new EventDetails();
                                }


                                ArrayList<EventDetails> stats = dataSnapshot.getValue(User.class).getEventLists();
                                /*for (EventDetails ED : stats) {
                                    System.out.println("event type: " + ED.getEventType());
                                }
                                landing.setUser(u);
                                landing.loginFunction(uid);
                            }
                            @Override
                            public void onCancelled(DatabaseError dbErr) {
                                Log.w("ERR", "failed to read value");
                            }
                        });*/

                    } else {

                        Log.w("TAG", "signInWithEmail:failure", task.getException());

                        Toast.makeText(landing, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        System.out.println(" " + this);
                    }
                }
            });
    }

//    public void


    /*
    TODO is there a better/ more efficent way ( right now there are 2 funcs in two diffrenet
            frages- actitvities that do basically the same )
     */
    public void resetFields(View view) {
        editEmail.setText("");
        editPassword.setText("");
    }
}




