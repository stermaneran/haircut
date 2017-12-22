package com.example.user.eran;

/**
 * Created by Sefi on 21/12/2017.
 */

public class Haircut {

    static final String path = "Haircuts";

    private String _name,_price, _duration;

    public Haircut(String name,String price, String duration){
        _name = name;
        _price = price;
        _duration = duration;
    }


    public String get_name() {
        return _name;
    }

    public static String get_path() {
        return path;
    }

    public String get_duration() {
        return _duration;
    }

    public String get_price(){
        return _price;
    }

    public String toString(){
        return  _name + "\n" + _price + "\n" + _duration + "\n";
    }



}

