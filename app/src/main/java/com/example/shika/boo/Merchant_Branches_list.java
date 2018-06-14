package com.example.shika.boo;

import models.listbranches;
import models.nearbyoffers;
import models.placebranch;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merchant_Branches_list extends AppCompatActivity {

    private ListView LV_branchesList ;
    private  Button toAddBranch ;
    ArrayList<String> listBranchNames1 ;

    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/showListOfBranches.php";

    private int placeId  ;

    //String [] branchNames = {"Alex","Cairo","Giza","Elbadrashen"};
    ArrayList<listbranches> listBranchNames ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branches_list);

        LV_branchesList = (ListView) findViewById(R.id.branchList);
        toAddBranch = (Button) findViewById(R.id.add);
        listBranchNames = new ArrayList<>();
         listBranchNames1 = new ArrayList<>();

        alertDialog = new android.app.AlertDialog.Builder(this).create();
        requestQueue = Volley.newRequestQueue(this);

       /* ArrayList<CustomBranchListItem> listnames =new ArrayList<>();
        for (int i=0 ; i<branchNames.length ; i++)
        {
            listnames.add(new CustomBranchListItem(branchNames[i]));

        }*/

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }

        showListOfBranchesFromDB(placeId);

        //adaptor = new arrayAdaptorHandle(this,listnames);
       // LV_branchesList.setAdapter(adaptor);

       toAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Merchant_Add_Branch.class);
                startActivity(intent);
            }
        });
       LV_branchesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String branchName =""+parent.getItemAtPosition(position);
               listbranches brache = new listbranches();
               String json = sharedPreferences.getString("Branches_Names", "");
               Gson gson = new Gson();
               Type type = new TypeToken<List<listbranches>>(){}.getType();
               listBranchNames= gson.fromJson(json, type);
               for(int i=0;i<listBranchNames.size();i++){
                   if(listBranchNames.get(i).getBrnachname().equals(branchName)){
                       brache = listBranchNames.get(i);
                   }
               }
              // Toast.makeText(Merchant_Branches_list.this, branchName, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplicationContext(),Merchant_Branch_Pass.class);
               intent.putExtra("BranchName",brache.getBrnachname());
               intent.putExtra("branchid",String.valueOf(brache.getBranchid()));
               startActivity(intent);
           }
       });

    }

    //public void toValidate(View vi){
       // Intent ba = new Intent(Merchant_Branches_list.this,Merchant_Branch_Pass.class);
      //  startActivity(ba);
  //  }

  public  void showListOfBranchesFromDB (final int place_Id){
        request = new StringRequest(Request.Method.POST,listOfBranchesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(Merchant_Branches_list.this, ""+response, Toast.LENGTH_SHORT).show();
                    if (jsonObject.names().get(0).equals("error"))
                    {
                        Toast.makeText(Merchant_Branches_list.this, ""+jsonObject.get("error"), Toast.LENGTH_SHORT).show();
                    }else if (jsonObject.names().get(0).equals("empty"))
                    {
                        Toast.makeText(Merchant_Branches_list.this, ""+jsonObject.get("empty"), Toast.LENGTH_SHORT).show();
                    }

                    JSONArray branches = jsonObject.getJSONArray("branchs");
                    for(int i=0 ; i<branches.length();i++)
                    {   listbranches branche=new listbranches();
                        JSONObject branch = branches.getJSONObject(i);
                        //Toast.makeText(Merchant_Branches_list.this,""+branch, Toast.LENGTH_SHORT).show();
                        listBranchNames1.add(branch.getString("Branch_name"));
                        branche.setBranchid(Integer.parseInt(branch.getString("Branch_id")));
                        branche.setBrnachname(branch.getString("Branch_name"));
                        listBranchNames.add(branche);

                    }
                    saveBNamesSharedPerf(listBranchNames);
                    saveBNamesSharedPerf1(listBranchNames1);
                    loadBNamesSharedPerf();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                alertDialog.setMessage("حدث خطأ بالاتصال بالشبكه؟" +"\n"+"يجب عليك فتح النت؟");
                alertDialog.show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("placeId",""+place_Id);
                return hashMap;
            }
        };

        requestQueue.add(request);
    }


    public  void saveBNamesSharedPerf(ArrayList<listbranches> listBranch)
    {
        Gson gson = new Gson();
        String json = gson.toJson(listBranch);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Branches_Names",json);
        editor.commit();
    }

    public void loadBNamesSharedPerf(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String x =sharedPreferences.getString("Branches_Names1",null);
        String[] s =x.split(",");
        ArrayList<String> BNameFromSharedP = new ArrayList<String>();
        for (int i=0 ;i<s.length;i++)
        {
            BNameFromSharedP.add(s[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,BNameFromSharedP);
        LV_branchesList.setAdapter(adapter);

    }
    public  void saveBNamesSharedPerf1(ArrayList<String> listBranch)
    {
        StringBuilder sb =new  StringBuilder();
        for (String s : listBranch)
        {
            sb.append(s);
            sb.append(",");
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("Branches_Names1",sb.toString());
        editor.commit();
    }


}
