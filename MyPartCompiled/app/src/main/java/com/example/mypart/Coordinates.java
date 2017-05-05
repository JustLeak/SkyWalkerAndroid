package com.example.mypart;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Вован on 04.05.17 004.
 */

public class Coordinates {
    public String name;
    public LatLng place;

    Coordinates(String n, double x, double y){
        this.name = n;
        this.place = new LatLng(x,y);
    }
}
