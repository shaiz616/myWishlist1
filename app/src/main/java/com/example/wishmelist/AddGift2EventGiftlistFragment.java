package com.example.wishmelist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.GiftItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**

 * A simple {@link Fragment} subclass.
 * Use the {@link AddGift2EventGiftlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGift2EventGiftlistFragment extends Fragment {

    String itemName, itemModel, link2Item, itemPrice, eventID, itemID ;
    EditText etName, etLink, etItemPrice, etItemModel;

    Button btn;
    GiftItem wish;

    MainActivity main;
    FirebaseDatabase db;
    DatabaseReference dbWishRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public AddGift2EventGiftlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AddGift2EventGiftlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGift2EventGiftlistFragment newInstance(String param1, String param2) {
        AddGift2EventGiftlistFragment fragment = new AddGift2EventGiftlistFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_gift2_event_giftlist, container, false);
        main = (MainActivity) getActivity();
        eventID = getArguments().getString("objID");
        System.out.println("in add new wish l 70, id = " + eventID);

        etName = view.findViewById(R.id.etItemName);
        etItemModel = view.findViewById(R.id.itemModel_et);
        etLink = view.findViewById(R.id.itemLink);
        etItemPrice = view.findViewById(R.id.et_itemPrice);

        btn = view.findViewById(R.id.BTNaddAWish);
        btn.setOnClickListener(v -> startFunction(view, true));

        btn = view.findViewById(R.id.BTNsaveAndFinish);
        btn.setOnClickListener(v -> captureWishData(view));

        db = FirebaseDatabase.getInstance();
        dbWishRef = db.getReference("event-list/" + eventID + "/wish-list");
        return view;
    }

    public void captureWishData(View view) {
        itemName = etName.getText().toString();
        link2Item = etLink.getText().toString();
        itemPrice = etItemPrice.getText().toString();
        itemModel = etItemModel.getText().toString();

        if(itemName == null || link2Item == null || itemPrice == null || itemModel == null ){
            Toast.makeText(this.getContext()," please fill all fields", Toast.LENGTH_LONG).show();
        } else {
            System.out.println("add a new gift, price is" + itemPrice);
            wish = new GiftItem(itemName, link2Item, itemModel, itemPrice);
            saveWish2DB(wish);
            Toast.makeText(this.getContext(),"New wish created successfully", Toast.LENGTH_LONG).show();
            main.switchFragment(new DisplayWishFragment(), eventID);
        }

//        return itemProps;
    }

    public void saveWish2DB(GiftItem wish) {
        System.out.println("in add wish l107, wish name =" + wish.getItemName());

        itemID = dbWishRef.push().getKey();
        dbWishRef.child(itemID).setValue(wish);

    }


    public void startFunction(View view, boolean flag) {
        System.out.println("the flag is " + flag);
        if(flag){
            System.out.println( ", so we will add another item");
        } else {
            System.out.println(", so we will finish here...");
        }

    }

}