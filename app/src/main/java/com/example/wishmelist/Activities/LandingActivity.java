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

public class LandingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public static User user;



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
    public void loginFunction() {
        String username = "anonimus";

        if(user == null) {
            System.out.println("user is null");
        } else {
            System.out.println("in landing activity line 42:  " + user.toString());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

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

    public void doSomething(View view) {
        System.out.println();
    }

}