package com.example.shika.boo.merchant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shika.boo.R;

public class MerchantFragmentHome extends Fragment {

    View myView ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.merchant_fragment_home,container,false);

        return myView;
    }
}
