package com.example.shika.boo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ByQuantity extends android.support.v4.app.Fragment {


    public ByQuantity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vq = inflater.inflate(R.layout.fragment_by_quantity, container, false);

        CheckBox someCheckBox= (CheckBox) vq.findViewById (R.id.chek);
        final EditText editText = (EditText) vq.findViewById(R.id.disedit);
        someCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                 editText.setEnabled(false);
                }
                else
                    editText.setEnabled(true);

            }
        });

        return vq;
    }

}
