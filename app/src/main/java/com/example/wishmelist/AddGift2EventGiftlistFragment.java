package com.example.wishmelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**

 * A simple {@link Fragment} subclass.
 * Use the {@link AddGift2EventGiftlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGift2EventGiftlistFragment extends Fragment {

    String itemName, link2Item;
    EditText etName, etLink;
    Button btn;

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

        etName = view.findViewById(R.id.itemName);
        etLink = view.findViewById(R.id.itemLink);

        btn = view.findViewById(R.id.addAWish);
        btn.setOnClickListener(v -> startFunction(view, true));

        btn = view.findViewById(R.id.saveAndFinish);
        btn.setOnClickListener(v -> startFunction(view, false));

        return view;
    }

    public String[] retrieveData(View view) {
        String[] itemProps = new String[2];


        return itemProps;
    }


    public void startFunction(View view, boolean flag) {
        System.out.println("the flaf is " + flag);
        if(flag){
            System.out.println( ", so we will add another item");
        } else {
            System.out.println(", so we will finish here...");
        }

    }

}