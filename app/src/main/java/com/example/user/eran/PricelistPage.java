package com.example.user.eran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PricelistPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PriceListAdapter adapter;
    private List<Haircut> listItems;
    private DividerItemDecoration itemDecoration;
    LinearLayoutManager layoutManager;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child(Haircut.get_path());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        listItems = new ArrayList<>();

//        mRef.push().setValue(new Haircut("Men's Haircut","20","15"));
//        mRef.push().setValue(new Haircut("Women's Haircut","35","25"));
//        mRef.push().setValue(new Haircut("Men's Beard Haircut","10","10"));
//        mRef.push().setValue(new Haircut("Hair drying","40","30"));
//        mRef.push().setValue(new Haircut("Hair dying","60","60"));
//        mRef.push().setValue(new Haircut("Hair rolling","45","180"));
//        mRef.push().setValue(new Haircut("Permanent straightening","700","240"));


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void showList(DataSnapshot dataSnapshot){
        for(DataSnapshot haircutEntry: dataSnapshot.getChildren()){
            Haircut haircut = JSONParser.getHaircutInstance(haircutEntry.getValue().toString());
            listItems.add(haircut);
        }
        adapter = new PriceListAdapter(listItems,this);
        recyclerView.setAdapter(adapter);
    }

}
