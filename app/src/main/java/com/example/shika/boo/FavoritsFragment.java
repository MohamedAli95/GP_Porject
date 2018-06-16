package com.example.shika.boo;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

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

import models.favorits;

public class FavoritsFragment extends android.app.Fragment implements com.example.shika.boo.FavoritsAdapter.OnItemClicked {

    RecyclerView FavoritsrecycleView;
    int user_id = 0;
    List<favorits> finaluserfavorits = new ArrayList<favorits>();
    FavoritsAdapter FavoritsAdapter;
    interestsInterfaces interfce = new interestsInterfaces();
    SharedPreferences sharedpreferences;
    String result;
    int favoritid = 0;


    private static final String FavoritsRetrive = "http://gp.sendiancrm.com/offerall/FavoritsRetrive.php";
    private static final String FavoritDelete = "http://gp.sendiancrm.com/offerall/deletefavorite.php";
    private static final String addfavorite = "http://gp.sendiancrm.com/offerall/addfavorite.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.favoritsfragment, container, false);

        MapsActivity.btn.setVisibility(View.GONE);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(vi.getContext());
        user_id = sharedpreferences.getInt("Id", 0);

        FavoritsrecycleView = (RecyclerView) vi.findViewById(R.id.FavoritsRecycler);
        FavoritsrecycleView.setHasFixedSize(true);
        FavoritsrecycleView.setLayoutManager(new LinearLayoutManager(vi.getContext()));
        FavoritsrecycleView.addItemDecoration(new DividerItemDecoration(vi.getContext(),
                DividerItemDecoration.VERTICAL));
        try {
            result = new loadfavoritsRetrive().execute().get();
            if (result.equals("Date not  found")) {

            } else {
                JSONArray favoirtss = new JSONArray(result);


                for (int i = 0; i < favoirtss.length(); i++) {
                    JSONObject favoritobject = favoirtss.getJSONObject(i);
                    favorits Favorit = new favorits();


                    Favorit.setPlace_id(Integer.valueOf(favoritobject.getString("Place_id")));
                    Favorit.setPlacename(favoritobject.getString("PLaceName"));
                    Favorit.setPlacelogo(favoritobject.getString("Place_LogoPhoto"));
                    Favorit.setFavoritid(Integer.valueOf(favoritobject.getString("Favorit_ID")));


                    finaluserfavorits.add(Favorit);
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        FavoritsAdapter = new FavoritsAdapter(vi.getContext(), finaluserfavorits);
        FavoritsrecycleView.setAdapter(FavoritsAdapter);
        FavoritsAdapter.setOnClick(this);


        return vi;
    }

    @Override
    public void onItemClick(int favoritdid) {
        favoritid=favoritdid;
        new deletefavorite().execute();

    }

    @Override
    public void onItemClick1(int placeid) {
        Intent intent = new Intent(getActivity().getApplicationContext(), Try.class);
        intent.putExtra("place_id",placeid);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);

    }




    private class loadfavoritsRetrive extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... Strings) {

            try {

                URL url = new URL(FavoritsRetrive);
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


            return null;

        }
    }

    private class deletefavorite extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... Strings) {

            try {

                URL url = new URL(FavoritDelete);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Favorit_ID", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(favoritid), "UTF-8");
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


            return null;

        }
        @Override
        protected void onPostExecute(String result) {
            finaluserfavorits = new ArrayList<favorits>();
            android.app.Fragment frg = null;
            frg = getActivity().getFragmentManager().findFragmentByTag("fragment");
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }


    }


}






