package com.example.shika.boo;

import android.content.Context;

import java.util.List;

import models.categorypage;

public class interestsInterfaces
{
    public VolleyCallback callback;
    public interface VolleyCallback{
        void onSuccess(List<categorypage> result);
    }
    public interface VolleyCallback1{
        void onSuccess1(List<Integer> result);
        void ondone(Context context);
    }


}
