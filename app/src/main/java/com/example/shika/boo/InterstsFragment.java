package com.example.shika.boo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import models.UserInterests;
import models.categorypage;

public class InterstsFragment extends android.app.Fragment implements Interestsadapter.OnItemClicked {
    RecyclerView interistsrecycleView;
    List<categorypage> categorylist;
    SharedPreferences sharedpreferences;
    String result;
    String result2;
    int user_id = 0;
    public List<Integer> userinterests;
    List<UserInterests> finaluserinterests = new ArrayList<UserInterests>();
    Interestsadapter interestsadapter;
    String type = "";
    String type1 = "";


    private static final String interestsRetrive = "http://gp.sendiancrm.com/offerall/interestsRetrive.php";
    private static final String userInterests = "http://gp.sendiancrm.com/offerall/userInterests.php";
    private static final String IntristsStringload = "http://gp.sendiancrm.com/offerall/interistsadd.php";

    @Override
    public void onDestroyView() {
        String json = new Gson().toJson(finaluserinterests);
        addIntrists(json,user_id,getActivity().getApplicationContext());
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vin = inflater.inflate(R.layout.activity_interestsactivity, container, false);

        MapsActivity.btn.setVisibility(View.GONE);


        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(vin.getContext());
        user_id = sharedpreferences.getInt("Id", 0);
        categorylist = new ArrayList<categorypage>();
        userinterests = new ArrayList<Integer>();

        interistsrecycleView = (RecyclerView) vin.findViewById(R.id.InteristsRecycler);
        interistsrecycleView.setHasFixedSize(true);
        interistsrecycleView.setLayoutManager(new LinearLayoutManager(vin.getContext()));
        interistsrecycleView.addItemDecoration(new DividerItemDecoration(vin.getContext(),
                DividerItemDecoration.VERTICAL));





        type1 = "loadInterestsRetrive";
        type = "userInterests";
        try {
            result= new loadInterestsRetrive().execute().get();
            if (result.equals("Date not  found")) {

            } else {
                List<Integer> userinterests1 = new ArrayList<Integer>();
                JSONArray categories = new JSONArray(result);


                for (int i = 0; i < categories.length(); i++) {
                    JSONObject categoryobject = categories.getJSONObject(i);
                    int userinterest = Integer.valueOf(categoryobject.getString("Category_id"));

                    userinterests.add(userinterest);




                }




            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            result2=new loadInterestsRetrive1().execute().get();
            if (result2.equals("Date not  found")) {


            } else {
                JSONArray categories = new JSONArray(result2);



                for (int i = 0; i < categories.length(); i++) {
                    JSONObject categoryobject = categories.getJSONObject(i);
                    categorypage category = new categorypage();


                    category.setCategory_id(Integer.valueOf(categoryobject.getString("Category_ID")));
                    category.setCategorytitle(categoryobject.getString("Name"));


                    categorylist.add(category);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setupAdapter(vin.getContext());

        return vin;
    }


    private void setupAdapter(Context context) {
        finaluserinterests = new ArrayList<UserInterests>();
        for (int i = 0; i < categorylist.size(); i++) {
            UserInterests interest = new UserInterests();
            interest.setCategory_id(categorylist.get(i).getCategory_id());
            interest.setCategorytitle(categorylist.get(i).getCategorytitle());
            interest.setInteriststatus(0);
            for (int j = 0; j < userinterests.size(); j++) {
                if (categorylist.get(i).getCategory_id() == userinterests.get(j)) {
                    interest.setInteriststatus(1);
                    break;
                }
            }
            finaluserinterests.add(interest);

        }

        interestsadapter = new Interestsadapter(context, finaluserinterests);
        interistsrecycleView.setAdapter(interestsadapter);
        interestsadapter.setOnClick(this);


    }

    @Override
    public void onItemClick(int status, int categoryid) {
        for (int i = 0; i < finaluserinterests.size(); i++) {
            if(finaluserinterests.get(i).getCategory_id()==categoryid){
                finaluserinterests.get(i).setInteriststatus(status);
            }
        }
    }


    private class loadInterestsRetrive extends AsyncTask<String, Void, String> {
       String resulttype="";



        @Override
        protected String doInBackground(String... Strings) {
            if (type.equals("userInterests")) {
                try {
                    resulttype = "userInterests";
                    URL url = new URL(userInterests);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(user_id), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;

        }



    }

    private class loadInterestsRetrive1 extends AsyncTask<String, Void, String> {
        String resulttype="";



        @Override
        protected String doInBackground(String... Strings) {
              if (type1.equals("loadInterestsRetrive")) {
                try {
                    resulttype = "loadInterestsRetrive";

                    URL url = new URL(interestsRetrive);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;

        }


    }

    public void addIntrists(final String IntristsString, final int user_id, final Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, IntristsStringload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("addresponse",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("IntristsString",""+IntristsString);
                params.put("user_id",""+user_id);


                return params;
            }
        };
        Volley.newRequestQueue(context).add(stringRequest);
    }

}
