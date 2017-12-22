package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;


public class DBPath {

    protected static DatabaseReference getDatabaseReference(DatabaseReference object,String path){
        String[] steps = path.split(">");
        return getDatabaseReference(object,steps,0);
    }

    private static DatabaseReference getDatabaseReference(DatabaseReference object,String[] keys,int index){
        if(index == keys.length-1) return object.child(keys[index]);
        return getDatabaseReference(object.child(keys[index]),keys,++index);
    }
}
