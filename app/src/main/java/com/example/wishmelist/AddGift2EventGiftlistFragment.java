package com.example.wishmelist;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wishmelist.Activities.MainActivity;
import com.example.wishmelist.Classes.GiftItem;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

        etName = view.findViewById(R.id.etItemName);
        etItemModel = view.findViewById(R.id.itemModel_et);
        etLink = view.findViewById(R.id.itemLink);
        etItemPrice = view.findViewById(R.id.et_itemPrice);

        btn = view.findViewById(R.id.BTNaddAWish);
        btn.setOnClickListener(v -> evaluteInput(true));

        btn = view.findViewById(R.id.BTNsaveAndFinish);
        btn.setOnClickListener(v -> evaluteInput(false));

        db = FirebaseDatabase.getInstance();
        dbWishRef = db.getReference("event-list/" + eventID + "/wish-list");
        return view;
    }

    public GiftItem captureWishData(ArrayList<EditText> arrlist) {
        String[] wishProps  = new String[4];
        for (int i = 0; i < 4; i++) {
            if (arrlist.get(i).getText().toString().isEmpty() ) {
                System.out.println(arrlist.get(i).getText().toString() +", str num " + i);
                Toast.makeText(this.getContext()," please fill all fields", Toast.LENGTH_LONG).show();
                return null;
            }
            wishProps[i] = arrlist.get(i).getText().toString();
        }

                             // item name,   item link,   item model,    price
        return new GiftItem(wishProps[0], wishProps[1], wishProps[2], wishProps[3]);
    }

    public void saveWish2DB(GiftItem wish) {
        System.out.println("in add wish l107, wish name =" + wish.getItemName());

        itemID = dbWishRef.push().getKey();
        dbWishRef.child(itemID).setValue(wish);

    }


    public void evaluteInput( boolean flag) {
        System.out.println("the flag is " + flag);
        ArrayList<EditText> etArray = createArray();

        GiftItem wish = captureWishData(etArray);
        if (wish != null) {

            saveWish2DB(wish);
            if(flag){
                System.out.println( ", so we will add another item");
//                main.switchFragment(new AddGift2EventGiftlistFragment(), eventID);
                main.resetFields(etArray);
//                resetFields(createArray());
            } else {
                System.out.println(", so we will finish here...\n " + this.toString());
                Toast.makeText(this.getContext(),"New wish created successfully", Toast.LENGTH_LONG).show();
                main.switchFragment(new DisplayWishFragment(), eventID, false);
            }

        }
        else
            main.popToast("in add wishes, something somewhere went wrong, wish is null");

    }

    private void resetFields(ArrayList<EditText> etField) {
        for(EditText et: etField) {
            et.setText("");
        }
    }

    public ArrayList<EditText> createArray() {
        ArrayList<EditText> temp = new ArrayList<EditText>();
        temp.add(etName) ;
        temp.add(etLink);
        temp.add(etItemModel);
        temp.add(etItemPrice);
        return temp;
    }

}