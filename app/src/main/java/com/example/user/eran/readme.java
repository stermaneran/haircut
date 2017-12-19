package com.example.user.eran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class readme extends AppCompatActivity {

    private TextView box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        box =(TextView)findViewById(R.id.textView);

        box.setMovementMethod(new ScrollingMovementMethod());


    }
}
