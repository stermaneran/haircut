package com.example.user.eran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class eranreadme extends AppCompatActivity {

    private TextView box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eran_activity_readme);

        box =(TextView)findViewById(R.id.textView);

        box.setMovementMethod(new ScrollingMovementMethod());


    }
}
