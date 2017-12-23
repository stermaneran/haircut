package com.example.user.eran;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AllApp extends AppCompatActivity {

    private ListView users;
    private ListAdapter myListAdapter;
    private final int CONTEXT_MENU_DELETE = 1;
    private final int CONTEXT_MENU_CANCEL = 2;
    private ArrayList<String> uidl = new ArrayList<>();
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Appointments");
    TextView mnoapps;


    private String selectedUid;

    private static final String TAG = "AppointmentsDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_app);
        users = (ListView) findViewById(R.id.listv);

        mnoapps = (TextView) findViewById(R.id.noapps);

        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        Map<String,Object> usersMap= (Map<String, Object>) dataSnapshot.getValue();

                        if(usersMap!=null) {
                            mnoapps.setVisibility(View.GONE);
                            collectEmails(usersMap);
                            users.setAdapter(myListAdapter);
                        }
                        else{
                            mnoapps.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


        users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList =(users.getItemAtPosition(position).toString());
                selectedUid= getUid(selectedFromList);
                registerForContextMenu(users);
                openContextMenu(users);
            }

        });

    }

    private String getUid(String selectedFromList) {
        int start = selectedFromList.indexOf("\n") +1;
        String mail=selectedFromList.substring(start);

        for (String id:uidl)
        {
            if (id.contains(mail))
            {
                String ans= id.substring(0, id.indexOf("="));
                return ans;
            }
        }
        return "null";
    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        //Context menu
        menu.setHeaderTitle("Menu");
        menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete Appointment");
        menu.add(Menu.NONE, CONTEXT_MENU_CANCEL, Menu.NONE, "Cancel");
    }

    @Override
    public boolean onContextItemSelected (MenuItem item){
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE: {
                try {
                    ref.child(selectedUid).removeValue();;
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

            case CONTEXT_MENU_CANCEL: {

            }
            break;
        }

        return super.onContextItemSelected(item);
    }

    private void collectEmails(Map<String,Object> users) {

        ArrayList<String> usersApp = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();

            String app =  (String) singleUser.get("Date") + "  " + (String) singleUser.get("Time") + "\n" +(String) singleUser.get("Email");
            usersApp.add(app);
            String uidnum = entry.toString();
            uidl.add(uidnum);

        }

        String[] users_arr = new String[usersApp.size()];
        usersApp.toArray(users_arr);
        myListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, users_arr);
    }


}