package com.example.wishmelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditUserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etPassword, etName;

    FirebaseDatabase db;
    DatabaseReference dbRef;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private User u;

    private Button btnSaveEditUser;
    private MainActivity main;


    public FragmentEditUserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEditUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditUserProfile newInstance(String param1, String param2) {
        FragmentEditUserProfile fragment = new FragmentEditUserProfile();
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
        View view = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
        etName = view.findViewById(R.id.etUserName);
        etPassword = view.findViewById(R.id.editUserPasswordInput);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference().child("plain-user");

        btnSaveEditUser = view.findViewById(R.id.saveEditUser);
        main = (MainActivity) getActivity();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        u = main.user;

        btnSaveEditUser.setOnClickListener(v -> retrieveInput());


        return view;
    }

    public void retrieveInput() {
        String pass, fName, temp;
        System.out.println( "456//" +  main.getUid());


        temp = etPassword.getText().toString();
        pass = temp.isEmpty() ? main.user.getPassword() : temp;

        if(validateInput(pass)) {
            user.updatePassword(pass);
        }else {
            main.popToast("Password not valid");
            return;
        }

        temp = etName.getText().toString();
        fName = temp.isEmpty() ? main.user.getName() : temp;
        System.out.println( pass + ", " + fName);

        main.user.setName(fName);
        main.user.setPassword(pass);
        saveToDb();
    }

    public boolean validateInput(String pw) {
        String ptrn = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)";
        if(pw.length() >= 6){
            Pattern regex = Pattern.compile(ptrn);
            Matcher myMatch = regex.matcher(pw);
            if(myMatch.find()) {
                return true;
            }
        }
        return false;
    }

    public void saveToDb() {
        System.out.println("prepare to upload 2 DB");
        System.out.println("new user name is: " + main.user.getName());
        dbRef.child(main.getUid() + "/name").setValue(main.user.getName());
        dbRef.child(main.getUid() + "/password").setValue(main.user.getPassword());

    }
}