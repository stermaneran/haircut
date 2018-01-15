package com.example.user.eran;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class registrationPage extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String imageEncoded;
    private StorageReference mStorageRef;
    private Uri filePath;
    private  String fname ;
    private  String lname ;
    private  String uname;
    private  String email ;
    private  String pass1 ;
    private  String pass2 ;
    private  String street;
    private  String city ;

    private FirebaseAuth mAuth;
    Button submitBtn;
    ImageButton uploadImageBtn;
    EditText unBox, passBox, cityBox, emailBox, streetBox, fnBox, lnBox, confPassBox;
    ToggleButton genderTgl;
    TextView profilePicTitle, registTitle, addrTitle;
    ImageView profilePictureView;

    FirebaseAnalytics mFirebaseAnalytics;


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
        lnBox = (EditText) findViewById(R.id.timebox);
        fnBox = (EditText) findViewById(R.id.datebox);
        profilePicTitle = (TextView) findViewById(R.id.profilePicTitle);
        registTitle = (TextView) findViewById(R.id.registTitle);
        addrTitle = (TextView) findViewById(R.id.addrTitle);
        genderTgl = (ToggleButton) findViewById(R.id.gender);
        profilePictureView = (ImageView) findViewById(R.id.profilePictureView);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileImageViewClick(v);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 fname = fnBox.getText().toString().trim();
                 lname = lnBox.getText().toString().trim();
                 uname = unBox.getText().toString().trim();
                 email = emailBox.getText().toString().trim();
                 pass1 = passBox.getText().toString().trim();
                 pass2 = confPassBox.getText().toString().trim();
                 street = streetBox.getText().toString().trim();
                 city = cityBox.getText().toString().trim();

                if (validateForm(fname, lname, uname, email, pass1, pass2, street, city)) {
                    mAuth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                if (imageEncoded == null)
                                    uploadFile();
                                else {
                                    String g = genderTgl.getText().toString();

                                    mFirebaseAnalytics.setUserProperty("my_city", city);
                                    mFirebaseAnalytics.setUserProperty("my_gender", g);

                                    Customer c =new Customer(fname, lname, uname, email, pass1, street, city, mAuth.getCurrentUser().getUid(), imageEncoded, g);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                     DatabaseReference ref = database.getReference("Users");
                                     ref.child(mAuth.getCurrentUser().getUid()).setValue(c);
                                    Intent myIntent = new Intent(registrationPage.this, logIn.class);
                                    startActivityForResult(myIntent, 0);
                                    finish();
                                }
                            } else {
                                toastMessage("invalid email");
                            }
                        }
                    });


                }
            }
        });
    }

    public void onProfileImageViewClick(View v) {
            final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(registrationPage.this);
		    builder.setTitle("Add Photo!");
		    builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result=Utils.checkPermission(registrationPage.this);

                    if (items[item].equals("Take Photo")) {
                        if(result)
                            cameraIntent();
                    } else if (items[item].equals("Choose from Library")) {
                        if(result)
                            galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                    }
                 }
            });
		    builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;
        }
        filePath=data.getData();
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePictureView.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 120, 120, false));
            Toast.makeText(getApplicationContext(), "Camera picture Uploaded ", Toast.LENGTH_LONG).show();
            encodeBitmapAndSaveToFirebase(imageBitmap);
    }

    private void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        ContentResolver cR = this.getContentResolver();
        String type = cR.getType(filePath);
        if (type!=null) {
            if (type.endsWith("jpeg") || type.endsWith("png") || type.endsWith("jpg")) {
                Bitmap bm = null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                profilePictureView.setImageBitmap(Bitmap.createScaledBitmap(bm, 120, 120, false));
            } else {
                toastMessage("invalid photo");
            }
        }
        else
        {
            if (filePath.toString().endsWith("jpeg") || filePath.toString().endsWith("png") || filePath.toString().endsWith("jpg")) {
                Bitmap bm = null;
                if (data != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                profilePictureView.setImageBitmap(Bitmap.createScaledBitmap(bm, 120, 120, false));
            } else {
                toastMessage("invalid photo");
            }
        }
    }

    // to check if user filled all the required fieds
    public boolean validateForm(String fname, String lname, String uname, String email, String pass1, String pass2, String street, String city) {
        boolean alldone = true;
        if (TextUtils.isEmpty(fname)) {
            fnBox.setError("Enter your first name");
            alldone = false;
        } else {
            fnBox.setError(null);
        }
        if (TextUtils.isEmpty(lname)) {
            lnBox.setError("Enter your last name");
            alldone = false;
        } else {
            lnBox.setError(null);
        }

        if (TextUtils.isEmpty(uname)) {
            unBox.setError("Enter your user name");
            alldone = false;
        } else {
            unBox.setError(null);
        }
        if (TextUtils.isEmpty(street)) {
            streetBox.setError("Enter your street address");
            alldone = false;
        } else {
            streetBox.setError(null);
        }
        if (TextUtils.isEmpty(city)) {
            cityBox.setError("Enter your city");
            alldone = false;
        } else {
            cityBox.setError(null);
        }


        if (TextUtils.isEmpty(email)) {
            emailBox.setError("invalid your email");
            alldone = false;
        } else {
            emailBox.setError(null);
        }

        if (TextUtils.isEmpty(pass1)) {
            passBox.setError("Enter your password");
            return false;
        } else {
            passBox.setError(null);
        }

        if (TextUtils.isEmpty(pass2)) {
            confPassBox.setError("Enter your password");
            return false;
        } else {
            confPassBox.setError(null);
        }

        if (pass1.length() < 6) {
            confPassBox.setError("password must be at least 6 characters");
            passBox.setError("password must be at least 6 characters");
            return false;
        } else {
            confPassBox.setError(null);
            passBox.setError(null);
        }

        if (!pass1.equals(pass2)) {
            confPassBox.setError("passwords dont match");
            passBox.setError("passwords dont match");
            return false;
        } else {
            confPassBox.setError(null);
            passBox.setError(null);
        }


        return alldone;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            StorageReference riversRef = mStorageRef.child("profiles/" + mAuth.getCurrentUser().getUid() + ".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "Picture Uploaded ", Toast.LENGTH_LONG).show();

                            mStorageRef.child("profiles/" + mAuth.getCurrentUser().getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    @SuppressWarnings("VisibleForTests")
                                    Uri  pic = taskSnapshot.getMetadata().getDownloadUrl();
                                    String g =genderTgl.getText().toString();

                                    mFirebaseAnalytics.setUserProperty("my_city", city);
                                    mFirebaseAnalytics.setUserProperty("my_gender", g);

                                    Customer c =new Customer(fname, lname, uname, email, pass1, street, city, mAuth.getCurrentUser().getUid(), pic.toString(),g);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference("Users");
                                    ref.child(mAuth.getCurrentUser().getUid()).setValue(c);
                                    Intent myIntent = new Intent(registrationPage.this, logIn.class);
                                    startActivityForResult(myIntent, 0);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    toastMessage("error uploading");
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            @SuppressWarnings("VisibleForTests")
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            String g =genderTgl.getText().toString();

            mFirebaseAnalytics.setUserProperty("my_city", city);
            mFirebaseAnalytics.setUserProperty("my_gender", g);

            Customer c = new Customer(fname, lname, uname, email, pass1, street, city, mAuth.getCurrentUser().getUid(),"https://firebasestorage.googleapis.com/v0/b/babershop-b43c6.appspot.com/o/profiles%2Fdefault.png?alt=media&token=9a5842a7-569e-4ca2-b0f8-6c5274933de4",g);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String uid = mAuth.getCurrentUser().getUid();
            ref.child("Users").child(uid).setValue(c);

            Intent myIntent = new Intent(registrationPage.this, logIn.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
    }
}