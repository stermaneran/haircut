package com.example.user.eran;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class RegistrationPage extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    private FirebaseAuth mAuth;
    Button submitBtn;
    ImageButton uploadImageBtn;
    EditText unBox, passBox, cityBox, emailBox, streetBox, fnBox, lnBox, confPassBox;
    TextView profilePicTitle, registTitle, addrTitle;
    ImageView profilePictureView;

    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        uploadImageBtn = (ImageButton) findViewById(R.id.uploadImageBtn);
        passBox = (EditText) findViewById(R.id.passBox);
        cityBox = (EditText) findViewById(R.id.cityBox);
        emailBox = (EditText) findViewById(R.id.editText5);
        streetBox = (EditText) findViewById(R.id.streetBox);
        confPassBox = (EditText) findViewById(R.id.confPassBox);
        unBox = (EditText) findViewById(R.id.unBox);
        lnBox = (EditText) findViewById(R.id.lnBox);
        fnBox = (EditText) findViewById(R.id.fnBox);
        profilePicTitle  = (TextView) findViewById(R.id.profilePicTitle);
        registTitle  = (TextView) findViewById(R.id.registTitle);
        addrTitle  = (TextView) findViewById(R.id.addrTitle);
        profilePictureView = (ImageView) findViewById(R.id.profilePictureView);


        mAuth = FirebaseAuth.getInstance();

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageViewClick(v);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fname = fnBox.getText().toString().trim();
                final String lname = lnBox.getText().toString().trim();
                final String uname = unBox.getText().toString().trim();
                final String email = emailBox.getText().toString().trim();
                final String pass1 = passBox.getText().toString().trim();
                final String pass2 = confPassBox.getText().toString().trim();
                final String street = streetBox.getText().toString().trim();
                final String city = cityBox.getText().toString().trim();

                if(validateForm(fname,lname,uname,email,pass1,pass2,street,city))
                {
                            mAuth.createUserWithEmailAndPassword(email,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        new eranCustomer(fname, lname, uname, email, pass1, street, city, mAuth.getCurrentUser().getUid(),uri).save();
                                        toastMessage("added user "+ email);
                                        Intent myIntent = new Intent(RegistrationPage.this, eranLogIn.class);
                                        startActivityForResult(myIntent, 0);
                                        finish();
                                    }
                                    else{
                                        toastMessage("invalid email");
                                    }
                                }
                             });
                }else{
                    //toastMessage("You didn't fill in all the fields.");
                }
            }
        });
    }


    public void onProfileImageViewClick(View v){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String pictureDirectoryPath = pictureDirectory.getPath();

            Uri data = Uri.parse(pictureDirectoryPath);

            photoPickerIntent.setDataAndType(data,"image/*");

            startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

                uri=imageUri.toString();

                toastMessage(uri);

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    uploadImageBtn.setVisibility(View.GONE);

                    // show the image to the user
                    profilePictureView.setImageBitmap(image);



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    // to check if user filled all the required fieds
    public boolean validateForm(String fname,String lname,String uname,String email,String pass1,String pass2,String street,String city)
    {
        boolean alldone=true;
        if(TextUtils.isEmpty(fname))
        {
            fnBox.setError("Enter your first name");
            alldone = false;
        }else
        {
            fnBox.setError(null);
        }
        if(TextUtils.isEmpty(lname))
        {
            lnBox.setError("Enter your last name");
            alldone = false;
        }else
        {
            lnBox.setError(null);
        }

        if(TextUtils.isEmpty(uname))
        {
            unBox.setError("Enter your user name");
            alldone = false;
        }else
        {
            unBox.setError(null);
        }
        if(TextUtils.isEmpty(street))
        {
            streetBox.setError("Enter your street address");
            alldone = false;
        }else
        {
            streetBox.setError(null);
        }
        if(TextUtils.isEmpty(city))
        {
            cityBox.setError("Enter your city");
            alldone = false;
        }else
        {
            cityBox.setError(null);
        }


        if(TextUtils.isEmpty(email))
        {
            emailBox.setError("invalid your email");
            alldone = false;
        }else
        {
            emailBox.setError(null);
        }

        if(TextUtils.isEmpty(pass1))
        {
            passBox.setError("Enter your password");
            return false;
        }else
        {
            passBox.setError(null);
        }

        if(TextUtils.isEmpty(pass2))
        {
            confPassBox.setError("Enter your password");
            return false;
        }else
        {
            confPassBox.setError(null);
        }

        if(pass1.length()<6)
        {
            confPassBox.setError("password must be at least 6 characters");
            passBox.setError("password must be at least 6 characters");
            return false;
        }else{
            confPassBox.setError(null);
            passBox.setError(null);
        }

        if(!pass1.equals(pass2))
        {
            confPassBox.setError("passwords dont match");
            passBox.setError("passwords dont match");
            return false;
        }else
        {
            confPassBox.setError(null);
            passBox.setError(null);
        }


        return alldone;
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
