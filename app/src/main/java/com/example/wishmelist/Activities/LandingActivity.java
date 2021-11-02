package com.example.wishmelist.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wishmelist.Classes.User;
import com.example.wishmelist.Landing_Fragments.LoginFragment;
import com.example.wishmelist.Landing_Fragments.RegisFragment;
import com.example.wishmelist.R;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static User user;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.landingFrame, new LoginFragment()).addToBackStack(null).commit();

    }
    /*
    public static void setUser(User u) {
        user = u;
    }*/


    /*
    TODO :  upon anonimus login send a signal to main
            to activiate the right response
            (for now display "anonimus" as username)
     */
    public void MoveToMainScreen(String uid) {
        String username = "anonimus";
        System.out.println("in landing activity line 49:  " + uid);

        if(user == null) {
            System.out.println("user is null");
        } else {
            System.out.println("in landing activity line 55:  " + user.toString());

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("uid", uid);
            startActivity(intent);
        }
    }

    public void setUser(User u) {
        this.user = u;
    }

    public static User getUser() {
        return user;
    }

    public void openSignup(View view) {

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landingFrame,new RegisFragment()).addToBackStack(null).commit();


/*        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);*/
    }

//    public void openMain(View view) {
//    }

    public static void logFunc(String lineNum) {
        Log.d("***  ///     line num:",  lineNum );
    }

    /*public void resetFields(View view) {
        editName.setText("");
        editEmail.setText("");
        editPassword.setText("");
    }*/

    public void doSomething(String context, String msg) {

        System.out.println("in " + context+ "activity. \n" +msg );
    }

}