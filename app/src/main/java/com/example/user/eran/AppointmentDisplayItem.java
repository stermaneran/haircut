package com.example.user.eran;

/**
 * Created by Sefi on 22/12/2017.
 */

public class AppointmentDisplayItem {

    String _date;
    String _time;
    String _custFullName;
    String _haircusStyle;
    String _appDuration;

    public AppointmentDisplayItem(String date, String time, String customerFullName ,String HC,String AD){
        _date = date;
        _time = time;
        _custFullName = customerFullName;
        _haircusStyle = HC;
        _appDuration = AD;
    }

    public String get_date() {
        return _date;
    }

    public String get_time() {
        return _time;
    }

    public String get_custFullName() {
        return _custFullName;
    }

    public String get_haircusStyle() {
        return _haircusStyle;
    }

    public String get_appDuration() {
        return _appDuration;
    }
}
