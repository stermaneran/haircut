package com.example.user.eran;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sefi on 21/12/2017.
 */

public class Haircut {

    public String _name, _price, _duration;

    public Haircut(String name, String price, String duration) {
        _name = name;
        _price = price;
        _duration = duration;
    }

    public Haircut() {
        //for firbase
    }

    public void build(){
        Haircut h = new Haircut("Men's Haircut","20","15");
        Haircut h1 = new Haircut("Women's Haircut","25","35");
        Haircut h2 = new Haircut("Men's Beard Haircut","10","10");
        Haircut h3 = new Haircut("Hair drying","400","30");
        Haircut h4 = new Haircut("Hair dying","60","60");
        Haircut h5 = new Haircut("Hair rolling","45","180");
        Haircut h6 = new Haircut("Permanent Hair Straightening","700","240");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Haircuts").child("H1").setValue(h);
        ref.child("Haircuts").child("H2").setValue(h1);
        ref.child("Haircuts").child("H3").setValue(h2);
        ref.child("Haircuts").child("H4").setValue(h3);
        ref.child("Haircuts").child("H5").setValue(h4);
        ref.child("Haircuts").child("H6").setValue(h5);
        ref.child("Haircuts").child("H7").setValue(h6);
    }

    public void foo(){

    }
}

