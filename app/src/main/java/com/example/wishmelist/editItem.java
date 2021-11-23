package com.example.wishmelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wishmelist.Activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editItem extends Fragment {

    String itemId, eventId;
    String[] strArr;

    EditText etName, etModel, etLink, etPrice;

    private Button btn;
    MainActivity main;

    public editItem() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static editItem newInstance(String param1, String param2) {
        editItem fragment = new editItem();

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
        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);

        String path = getArguments().getString("objID");
        strArr = path.split(" ");
        eventId = strArr[0]; itemId=strArr[1];
        main = (MainActivity) getActivity();

        etName = view.findViewById(R.id.editTextNameItem);
        etModel = view.findViewById(R.id.etItemModel);
        etLink = view.findViewById(R.id.EditTextLinkItem);
        etPrice = view.findViewById(R.id.etItemPrice);

        btn = view.findViewById(R.id.btnConfirmEditWish);
        String str ="Haveyou done all the changes that you wanted?\n" +
                "press confirm to finish and go back\n" +
                "or cancel to make other changes";
        btn.setOnClickListener(v -> main.popupDialog(this, str, itemId ));



        return view;
    }


    public void retriveWishChanges() {
        System.out.println("prepare to edit wish");

    }
}