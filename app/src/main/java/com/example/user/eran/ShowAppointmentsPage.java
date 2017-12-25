package com.example.user.eran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowAppointmentsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Appointment> listItems;
    private List<AppointmentDisplayItem> displayList;
    private DividerItemDecoration itemDecoration;
    LinearLayoutManager layoutManager;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        listItems = new ArrayList<>();
        displayList = new ArrayList<>();
    }

}

