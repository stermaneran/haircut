package com.example.user.eran;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class usersPage extends AppCompatActivity {

    private ListView users;
    private ListAdapter myListAdapter;
    private final int CONTEXT_MENU_ADMIN = 1;
    private final int CONTEXT_MENU_NON_ADMIN = 2;
    private final int CONTEXT_MENU_CANCEL = 3;
    //private ArrayList<String> uidl = new ArrayList<>();
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("customer");

    private static final String TAG = "usersDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_page);
        users = (ListView) findViewById(R.id.listv);


        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectEmails((Map<String,Object>) dataSnapshot.getValue());
                        users.setAdapter(myListAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


        users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedFromList =(users.getItemAtPosition(position).toString());

                registerForContextMenu(users);
                openContextMenu(users);

            }

        });

    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo){
        //Context menu
        menu.setHeaderTitle("Menu");
        menu.add(Menu.NONE, CONTEXT_MENU_ADMIN, Menu.NONE, "Set Admin");
        menu.add(Menu.NONE, CONTEXT_MENU_NON_ADMIN, Menu.NONE, "Set Non-Admin");
        menu.add(Menu.NONE, CONTEXT_MENU_CANCEL, Menu.NONE, "Cancel");
    }

    @Override
    public boolean onContextItemSelected (MenuItem item){
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case CONTEXT_MENU_ADMIN: {
                try {
                            ref.child("i4KSlj4mE9Mf51xxcc3HOM9bfmg1").child("Type").setValue("Admin");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
            }
            break;
            case CONTEXT_MENU_NON_ADMIN: {
                try {
                    ref.child("i4KSlj4mE9Mf51xxcc3HOM9bfmg1").child("Type").setValue("Non-Admin");
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

        ArrayList<String> usersEmail = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();

            String mail =  (String) singleUser.get("Email") + "\n" + (String) singleUser.get("Type");
//            String uidnum = entry.toString();
//            uidl.add(uidnum);
            usersEmail.add(mail);

        }

        String[] users_arr = new String[usersEmail.size()];
        usersEmail.toArray(users_arr);
        myListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, users_arr);
          }


}