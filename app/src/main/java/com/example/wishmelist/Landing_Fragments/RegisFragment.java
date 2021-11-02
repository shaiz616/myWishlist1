package com.example.wishmelist.Landing_Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wishmelist.Activities.LandingActivity;
import com.example.wishmelist.Classes.User;
import com.example.wishmelist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisFragment extends Fragment {

    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;

    private LandingActivity landing ;

    private FirebaseAuth mAuth;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisFragment newInstance(String param1, String param2) {
        RegisFragment fragment = new RegisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
            System.out.println(currentUser.toString());
        }
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regis, container, false);

        editName = view.findViewById(R.id.editTxtName);
        editEmail = view.findViewById(R.id.editTxtEmail);
        editPassword = view.findViewById(R.id.editPassword);

        Button btnReset = view.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> resetFields());


        landing = (LandingActivity) getActivity();

        Button btn = view.findViewById(R.id.btnConfirmRegis);
        btn.setOnClickListener(v -> captureInput(view));

        return view;
    }

    private void signupFunc(User u) {
        System.out.println("line 130 in regis: " + u.getName());
        mAuth.createUserWithEmailAndPassword(u.getEmail(), u.getPassword())
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = db.getReference("plain-user").child(uid);
                            myRef.setValue(u);
                            Toast.makeText(landing, "Authentication ok", Toast.LENGTH_LONG).show();
                            landing.MoveToMainScreen(uid);
//                            login();
//                updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("warning", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(landing, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                updateUI(null);
                        }
                    }
                });

    }

//    public void login() {
//        landing.loginFuncton();
//    }

    /*
    btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                confirmRegis(view);
            }
        });
     */
    public void captureInput(View view) {


        String name = editName.getText().toString();
        /*
        prevent option to create 2 or more users with the same email
        * */
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

//        editName.setText("");
         if( validateInput(name, email, password) ) {
             System.out.println("line 182 all input is good");
             User u = new User(name, password, email);
             landing.setUser(u);
             System.out.println("line 185 in regis: " + u.getName());
             signupFunc(u);
//             landing.loginFuncton(view);
         }
    }

    public boolean validateInput(String name, String email, String pw) {

        String ptrn = "^(?=.*[a-zA-Z])(?=\\S+$)";
//        boolean flag = validation(name, 4, ptrn);
        if (!validation(name, 6, ptrn)) {
            System.out.println( name + " is not good @line 195 ");
            return false;
        }


        ptrn = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)";
//        flag = validation(pw, 6, ptrn);

        if (!validation(pw, 3, ptrn)) {
            System.out.println( pw + " is not good @line 204 ");
            return false;
        }

        ptrn = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
//        flag = validation(email, 5, ptrn);
        if (!validation(email, 5, ptrn)) {
            System.out.println( email + " is not good @line 211 ");
            return false;
        }

        return true;
    }

    public Boolean validation(String input, int minLength, String ptrn) {

        if(input.length() >= minLength) {
            Pattern myregex = Pattern.compile(ptrn);
            Matcher myMatch = myregex.matcher(input);
            if(myMatch.find()) {
                return true;
            }

            // add Toast
        }
        return false;
    }

    public void resetFields() {
        editName.setText("");
        editEmail.setText("");
        editPassword.setText("");
    }
}