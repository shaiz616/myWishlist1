package com.example.wishmelist.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wishmelist.Classes.User;
import com.example.wishmelist.Landing_Fragments.LoginFragment;
import com.example.wishmelist.Landing_Fragments.RegisFragment;
import com.example.wishmelist.R;
import com.google.firebase.auth.FirebaseUser;

public class LandingActivity extends AppCompatActivity {

    private String uid;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static User user;
    private FirebaseUser firebaseUser;

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        String umail, upw, userId;
        sharedPref = getPreferences(MODE_PRIVATE);

        System.out.println("in oncreate l 40 uid = " + uid);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (uid == null) {
            System.out.println("logout");

            editor.putString("userKey", null);
            editor.apply();
        }

        if (sharedPref.getString("userKey" , null) == "") {
            System.out.println("key is null");
            Toast.makeText(this, "key is null", Toast.LENGTH_LONG).show();
        }
        System.out.println("treq2 | userkey = " + sharedPref.getString("userKey", null));


        if (sharedPref.getString("userKey", null) != null) {
            System.out.println("treq | idkey = " + sharedPref.getString("idKey", null));

            Toast.makeText(this, "userkey = " + sharedPref.getString("userKey", null), Toast.LENGTH_LONG)   .show();
            userId = sharedPref.getString("idKey",null);

            umail = sharedPref.getString("userKey", null);
            upw = sharedPref.getString("pwKey", null);
            System.out.println("in landing, l 48, mail = " + umail);
            setUid(userId);
            doSomething("in landing/oncreate l-50\n userkey = " + umail);

            MoveToMainScreen();
//            keepLoginData(umail, upw, userId);


        }

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.landingFrame, new LoginFragment()).addToBackStack(null).commit();


    }
    /*
    public static void setUser(User u) {
        user = u;
    }*/


    /*
    TODO :  upon anonymous login send a signal to main
            to activate the right response
            (for now display "anonymous" as username)
            comment instead of dani
     */
    public void MoveToMainScreen() {
        String username = "anonimus";
        /*if(user == null) {
            System.out.println("user is null");
        } else {
            System.out.println("in landing activity line 76:  " + user.toString());


        }*/

        System.out.println("in move l-93\nuid = " + getUid());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("uid", this.getUid());
        startActivity(intent);
    }

    public void keepLoginData(String umail, String upw, String userId) {

        if (uid == null) {
            System.out.println("zasx | uid is null " + uid);
        } else {
            System.out.println("poiu | uid not null " + uid);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("userKey", umail);
            editor.putString("pwKey", upw);
            editor.putString("idKey", userId);
            editor.apply();


        }
        if (!userId.isEmpty()){
            System.out.println("zxca | pref not null \n userid =" + userId );
            MoveToMainScreen();
        }
    }

    public void setUid(String uid) { this.uid = uid; }

    public String getUid() { return this.uid; }


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

    public void doSomething(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        System.out.println("in landing activity\n" + msg );
    }

}