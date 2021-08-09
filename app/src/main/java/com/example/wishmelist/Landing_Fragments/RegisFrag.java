package com.example.wishmelist.Landing_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wishmelist.Activities.LandingActivity;
import com.example.wishmelist.Classes.User;
import com.example.wishmelist.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisFrag extends Fragment {

    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;

    private String name;
    private String email;
    private String password;

    private LandingActivity landing ;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisFrag newInstance(String param1, String param2) {
        RegisFrag fragment = new RegisFrag();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regis, container, false);

        landing = (LandingActivity) getActivity();

        Button btn = view.findViewById(R.id.btnConfirmRegis);
        btn.setOnClickListener(v -> confirmRegis(view));

        return view;
    }

    /*
    btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                confirmRegis(view);
            }
        });
     */
    public void confirmRegis(View view) {

        editName = view.findViewById(R.id.editTxtName);
        editEmail = view.findViewById(R.id.editTxtEmail);
        editPassword = view.findViewById(R.id.editTxtPassword);

        name = editName.getText().toString();
        /*
        prevent option to create 2 or more users with the same email
        * */
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

         if( validateInput(name, email, password) ) {
             System.out.println(" all input is good");
             User u = new User(name, password, email);
             landing.loginFuncton(view);
         }


    }

    public boolean validateInput(String name, String email, String pw) {

        String ptrn = "^(?=.*[a-zA-Z])(?=\\S+$)";
//        boolean flag = validation(name, 4, ptrn);
        if (!validation(name, 4, ptrn)) {
            System.out.println( name + " is not good @line 120 ");
            return false;
        }


        ptrn = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)";
//        flag = validation(pw, 6, ptrn);
        if (!validation(pw, 8, ptrn)) {
            System.out.println( pw + " is not good @line 130 ");
            return false;
        }

        ptrn = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
//        flag = validation(email, 5, ptrn);
        if (!validation(email, 5, ptrn)) {
            System.out.println( email + " is not good @line 137 ");
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
}