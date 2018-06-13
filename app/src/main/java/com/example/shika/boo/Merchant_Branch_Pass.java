package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import  models.placebranch ;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Merchant_Branch_Pass extends AppCompatActivity {
    TextView TV_branchName ;
    EditText ET_password ;
    placebranch classBranch ;

    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String loginBranchesURL ="http://gp.sendiancrm.com/offerall/loginBranch.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__pass);
        alertDialog = new AlertDialog.Builder(this).create();
        requestQueue = Volley.newRequestQueue(this);

        TV_branchName =(TextView) findViewById(R.id.bName) ;
        ET_password = (EditText) findViewById(R.id.password);


        String branch_Name = getIntent().getExtras().getString("BranchName");
        TV_branchName.setText(branch_Name);

        Button conformPassword = (Button) findViewById(R.id.confirmPass);

        conformPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validate())
                {
                    final String password = ET_password.getText().toString();
                    loginBranch(password);
                }


            }
        });
    }

    public  Boolean validate(){

        boolean valid = true ;

        if (ET_password.getText().toString().matches("")){ // || ET_password.length()<8
            ET_password.setError("Enter Valid Password!");
            valid = false ;
        }

        return valid ;
    }

    public  void loginBranch(final String Bpassword){
        request=new StringRequest(Request.Method.POST, loginBranchesURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //Toast.makeText(Merchant_Branch_Pass.this, ""+response, Toast.LENGTH_SHORT).show();

                    if (jsonObject.names().get(0).equals("error"))
                    {
                        Toast.makeText(Merchant_Branch_Pass.this, ""+jsonObject.get("error"), Toast.LENGTH_SHORT).show();
                    }else if (jsonObject.names().get(0).equals("empty"))
                    {
                        Toast.makeText(Merchant_Branch_Pass.this, ""+jsonObject.get("empty"), Toast.LENGTH_SHORT).show();
                    }

                    JSONArray branches = jsonObject.getJSONArray("branchEs");
                    JSONObject branch = branches.getJSONObject(0);

                    classBranch = new placebranch();
                    classBranch.setBranchName((String)branch.getString("Branch_name"));
                    classBranch.setId(Integer.parseInt((String) branch.getString("Branch_id")));
                    classBranch.setPlace_id(Integer.parseInt((String) branch.getString("Place_id")));
                    classBranch.setRewordSystem(Integer.parseInt((String) branch.getString("RewardSystemAvailabilty")));
                    classBranch.setPassword((String)branch.getString("Branch_Password"));
                    classBranch.setPhone((String)branch.getString("Branch_phone"));
                    classBranch.setLatitude(Float.parseFloat((String) branch.getString("latitude")));
                    classBranch.setLongitude(Float.parseFloat((String) branch.getString("longitude")));

                    BranchSesionStart(classBranch);

                    if (sharedPreferences != null)
                    {
                        int decide =branch.getInt("RewardSystemAvailabilty");
                        if(decide==1) {
                            Intent intent = new Intent(getApplicationContext(), Merchant_Branch_Home.class);
                            startActivity(intent);
                        }
                        else{

                            Intent intent = new Intent(getApplicationContext(), Merchant_Branch_Home.class);
                            intent.putExtra("decide",2);
                            startActivity(intent);
                        }
                        alertDialog.setMessage("Welcome: "+sharedPreferences.getString("BName",null)+" "+"Branch");
                        alertDialog.show();
                    }else {
                        Toast.makeText(getApplicationContext(), "sharedPreferences=null", Toast.LENGTH_SHORT).show();
                        alertDialog.setMessage("Wrong UserName Or Password");
                        alertDialog.show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
                alertDialog.setMessage("حدث خطأ بالاتصال بالشبكه؟" +"\n"+"يجب عليك فتح النت؟");
                alertDialog.show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap =new HashMap<>();
                hashMap.put("password",Bpassword);
                return hashMap;
            }
        };

        requestQueue.add(request);
    }

    public void BranchSesionStart(placebranch branch){

        sharedPreferences = getSharedPreferences("branche", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("BId",branch.getId());
        editor.putString("BName",branch.getBranchName());
        editor.putInt("BplaceId",branch.getPlace_id());
        editor.putString("BPassword",branch.getPassword());
        editor.putInt("rewordSystem",branch.getRewordSystem());
        editor.putString("BPhone",branch.getPhone());
        editor.putFloat("Blatitude",branch.getLatitude());
        editor.putFloat("BLongitude",branch.getLongitude());
        editor.commit();
    }

}