package com.example.user.eran;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Sefi on 22/12/2017.
 */

public class DSPath {

    protected static DataSnapshot getDatabaseSnapshot(DataSnapshot data, String path){
        String[] steps = path.split(">");
        return getDatabaseSnapshot(data,steps,0);
    }

    private static DataSnapshot getDatabaseSnapshot(DataSnapshot object,String[] keys,int index){
        if(index == keys.length-1) return object.child(keys[index]);
        return getDatabaseSnapshot(object.child(keys[index]),keys,++index);
    }

}
