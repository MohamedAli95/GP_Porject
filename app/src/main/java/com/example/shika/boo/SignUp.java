package com.example.shika.boo;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    String [] Gender = {"male","female"};

    Button siup;



    private ImageButton pick_image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView profilepic;
    EditText username;
    EditText userpassword;
    EditText useremail;
    EditText userphone;
    EditText userage;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private String encoded_string;
    MaterialBetterSpinner betterSpinner ;
    Date date=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username=(EditText) findViewById(R.id.username_reg);
        userage=(EditText) findViewById(R.id.userage_reg);
        userphone=(EditText) findViewById(R.id.userphone_reg);
        useremail=(EditText) findViewById(R.id.useremail_reg);
        userpassword=(EditText) findViewById(R.id.userpassword_reg);
        siup = (Button) findViewById(R.id.button_signup);
        useremail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        pick_image=(ImageButton) findViewById(R.id.PickImage);
        profilepic=(ImageView)findViewById(R.id.profilepic);
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Gender);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.sp1);
        betterSpinner.setAdapter(arrayAdapter);
        encoded_string="";




    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bit= MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap =  Bitmap.createScaledBitmap(bit, 380, 420, true);;
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilepic.setImageBitmap(bitmap2);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            new Encode_image().execute();
        }
    }
    public void RegisterUser(View v){
        if(validate()) {
            String name = username.getText().toString();
            String age = userage.getText().toString();
            String password = userpassword.getText().toString();
            String email = useremail.getText().toString();
            String phone = userphone.getText().toString();
            String gender = betterSpinner.getText().toString();
            String lastlogin = formatter.format(date);
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, name, age, password, email, phone, gender, lastlogin, encoded_string);
        }

    }



    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
           /* if(bitmap==null){
                bitmap=((BitmapDrawable)profilepic.getDrawable()).getBitmap();
            }*/

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }
    }
    public boolean validate() {
        boolean valid = true;
        if(useremail.getText().equals("")||!android.util.Patterns.EMAIL_ADDRESS.matcher(useremail.getText()).matches()){
            useremail.setError("Please Enter Valid Email Adress");
            valid=false;
        }
        if(userpassword.getText().toString().matches("")||userpassword.length()<8){
            userpassword.setError("please enter password with at least 8 characters ");
            valid=false;
        }
        if(username.getText().toString().matches("")||username.length()>32){
            username.setError("please enter Valid Name");
            valid=false;
        }
        if(userphone.getText().toString().matches("")){
            userphone.setError("please enter Phone");
            valid=false;
        }
        if(userage.getText().toString().matches("")){
            userage.setError("please enter Age");
            valid=false;
        }
        return valid;
    }
}
