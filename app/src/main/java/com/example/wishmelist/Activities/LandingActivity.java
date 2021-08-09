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
import com.example.wishmelist.Landing_Fragments.RegisFrag;
import com.example.wishmelist.R;

public class LandingActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.landingFrame, new LoginFragment()).addToBackStack(null).commit();

    }

    public void loginFuncton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void openSignup(View view) {

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.landingFrame,new RegisFrag()).addToBackStack(null).commit();


/*        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);*/
    }

//    public void openMain(View view) {
//    }

    public static void logFunc(String lineNum) {
        Log.d("***  ///     line num:",  lineNum );
    }

}