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

/**
 * /this activity presents all users in the database for Admin users only
 */
public class AllUsers extends AppCompatActivity {

    private ListView users;
    private ListAdapter myListAdapter;
    private final int CONTEXT_MENU_ADMIN = 1;
    private final int CONTEXT_MENU_NON_ADMIN = 2;
    private final int CONTEXT_MENU_CANCEL = 3;
    private ArrayList<String> uidl = new ArrayList<>();
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
    private String selectedUid;

    private static final String TAG = "usersDatabase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_page);
        users = (ListView) findViewById(R.id.listv);

//listener to firebase reference
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectEmails((Map<String, Object>) dataSnapshot.getValue());
                        users.setAdapter(myListAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

//on click of an user
        users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (users.getItemAtPosition(position).toString());
                selectedUid = getUid(selectedFromList);
                registerForContextMenu(users);
                openContextMenu(users);
            }

        });

    }

    /**
     * finds uid in given string
     *
     * @param selectedFromList the user selected
     * @return users uid given by the authentication
     */
    private String getUid(String selectedFromList) {
        int start = selectedFromList.indexOf("\n") + 1;
        int end = selectedFromList.lastIndexOf("\n");
        String mail = selectedFromList.substring(start, end);

        for (String id : uidl) {
            if (id.contains(mail)) {
                String ans = id.substring(0, id.indexOf("="));
                return ans;
            }
        }
        return "null";
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View
            v, ContextMenu.ContextMenuInfo menuInfo) {
        //Context menu
        menu.setHeaderTitle("Menu");
        menu.add(Menu.NONE, CONTEXT_MENU_ADMIN, Menu.NONE, "Set Admin");
        menu.add(Menu.NONE, CONTEXT_MENU_NON_ADMIN, Menu.NONE, "Set Non-Admin");
        menu.add(Menu.NONE, CONTEXT_MENU_CANCEL, Menu.NONE, "Cancel");
    }

    /**
     * setting user type to Admin/Non-Admin
     *
     * @param item options from choise (set Admin/set Non-Admin/cancel) to set
     * @return true upon success
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_ADMIN: {
                try {
                    ref.child(selectedUid).child("Type").setValue("Admin");
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case CONTEXT_MENU_NON_ADMIN: {
                try {
                    ref.child(selectedUid).child("Type").setValue("Non-Admin");
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

    /**
     * extracts the relevant data from the appointment
     *
     * @param users map of appointment objects
     */
    private void collectEmails(Map<String, Object> users) {

        ArrayList<String> usersEmail = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();

            String mail = (String) singleUser.get("LastName") + ", " + (String) singleUser.get("FirstName") + "\n" + (String) singleUser.get("Email") + "\n" + (String) singleUser.get("Type");
            String uidnum = entry.toString();
            uidl.add(uidnum);
            usersEmail.add(mail);

        }

        String[] users_arr = new String[usersEmail.size()];
        usersEmail.toArray(users_arr);
        myListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, users_arr);
    }

}