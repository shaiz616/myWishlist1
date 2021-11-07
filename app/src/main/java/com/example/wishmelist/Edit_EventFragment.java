package com.example.wishmelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.wishmelist.Classes.EventDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_EventFragment extends Fragment {

    String eventId, eventName, eventAddress;
    EditText etName, etAddress;

    Button btn;
    EventDetails event;


    public Edit_EventFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static Edit_EventFragment newInstance() {
        Edit_EventFragment fragment = new Edit_EventFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_edit__event, container, false);

        etName = view.findViewById(R.id.editEvent_etName);
        etAddress = view.findViewById(R.id.editEvent_etAddress);

        btn = view.findViewById(R.id.btnConfirmEditEvent);
        btn.setOnClickListener(v -> retriveChanges());

        return view;
    }

    public void retriveChanges(){

    }
}