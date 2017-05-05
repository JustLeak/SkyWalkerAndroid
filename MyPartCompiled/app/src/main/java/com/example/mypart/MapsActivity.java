package com.example.mypart;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.SphericalUtil;
import java.util.LinkedList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng firstClicked = null;
    private LatLng secondClicked = null;
    LinkedList <Coordinates> listOfSights;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //gpsTracker =new GPSTracker(getApplicationContext());
        //mLocation = gpsTracker.getLocation();

        //latitude=mLocation.getLatitude();
        //longitude=mLocation.getLongitude();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(MapsActivity.this, MainMenu.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMarkers(LinkedList<Coordinates> list, boolean isSight){
        for(Coordinates el : list){
            if (isSight){
                mMap.addMarker(new MarkerOptions()
                        .position(el.place)
                        .rotation(0.1f)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title(el.name));
            }
            else{
                mMap.addMarker(new MarkerOptions()
                        .position(el.place)
                        .title(el.name));
            }

        }
    }

    private boolean isNear(LatLng curPos, LatLng objPos){
        //Change distance!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (SphericalUtil.computeDistanceBetween(curPos, objPos) <= 50000){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Draw Polyline between two selected markers
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mark) {
                if (mark.getRotation() == 0){
                    PolylineOptions polyline=new PolylineOptions()
                            .geodesic(true)
                            .color(Color.RED);
                    if (firstClicked == null){
                        firstClicked = mark.getPosition();
                    }
                    else if (secondClicked == null) {
                        secondClicked = mark.getPosition();
                        polyline.add(firstClicked);
                        polyline.add(secondClicked);
                        mMap.addPolyline(polyline);

                        double delta = 6000 / SphericalUtil.computeDistanceBetween(firstClicked, secondClicked);
                        double coef = 0;
                        do {
                            LatLng b = SphericalUtil.interpolate(firstClicked, secondClicked, coef);
                            for(Coordinates i : listOfSights){
                                if (isNear(b,i.place)){
                                    mMap.addMarker(new MarkerOptions()
                                            .position(i.place)
                                            .rotation(0.1f)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                            .title(i.name));
                                }
                            }
                            coef += delta;
                        } while(coef <=1 + delta);




                    }
                    else{
                        secondClicked = null;
                        firstClicked = mark.getPosition();
                    }
                }
                else if (mark.getRotation() != 0){
                    try {
                        Intent intent = new Intent(MapsActivity.this, Sights.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                    e.printStackTrace();
                        }
                    }
                return false;
                        }
                        });
                        //Fill array with some markers
                        LinkedList <Coordinates> listOfAirports = new LinkedList<>();
        listOfAirports.add(new Coordinates("Schiphol",52.3086,4.763889));
        listOfAirports.add(new Coordinates("Hartsfield Jackson Atlanta Intl",33.636719,-84.428067));
        listOfAirports.add(new Coordinates("Barcelona",41.297078,2.078464));
        listOfAirports.add(new Coordinates("Chhatrapati Shivaji Intl",19.088686,72.867919));
        listOfAirports.add(new Coordinates("Baiyun Intl",23.392436,113.298786));
        listOfAirports.add(new Coordinates("Charles De Gaulle",49.012779,2.55));
        listOfAirports.add(new Coordinates("Denver Intl",39.861656	,-104.673178));
        listOfAirports.add(new Coordinates("Dubai Intl",25.252778,55.364444));
        listOfAirports.add(new Coordinates("Frankfurt Main",50.026421,8.543125));
        listOfAirports.add(new Coordinates("Hong Kong Intl",22.308919,113.914603));
        listOfAirports.add(new Coordinates("Tokyo Intl",35.552258,139.779694));
        listOfAirports.add(new Coordinates("George Bush Intercontinental",29.984433,-95.341442));
        listOfAirports.add(new Coordinates("Incheon Intl",37.469075,126.450517));
        listOfAirports.add(new Coordinates("Ataturk",40.976922,28.814606));
        listOfAirports.add(new Coordinates("John F Kennedy Intl",40.639751,-73.778925));
        listOfAirports.add(new Coordinates("Los Angeles Intl",33.942536,-118.408075));
        listOfAirports.add(new Coordinates("Gatwick",51.148056,-0.190278));
        listOfAirports.add(new Coordinates("Heathrow",51.4775,-0.461389));
        listOfAirports.add(new Coordinates("Barajas",40.493556,-3.566764));
        listOfAirports.add(new Coordinates("Orlando Intl",28.429394,-81.308994));
        listOfAirports.add(new Coordinates("Miami Intl",25.79325,-80.290556));
        listOfAirports.add(new Coordinates("Minneapolis St Paul Intl",44.881956,-93.221767));
        listOfAirports.add(new Coordinates("Franz Josef Strauss",48.353783,11.786086));
        listOfAirports.add(new Coordinates("Narita Intl",35.764722,140.386389));
        listOfAirports.add(new Coordinates("Chicago Ohare Intl",41.978603,-87.904842));
        listOfAirports.add(new Coordinates("Capital Intl",40.080111,116.584556));
        listOfAirports.add(new Coordinates("Pudong",31.143378,121.805214));
        listOfAirports.add(new Coordinates("San Francisco Intl",37.618972,-122.374889));
        listOfAirports.add(new Coordinates("Changi Intl",1.350189,103.994433));
        listOfAirports.add(new Coordinates("Sydney Intl",-33.946111,151.177222));
        listOfAirports.add(new Coordinates("Taoyuan Intl",25.077731,121.232822));
        listOfAirports.add(new Coordinates("Sheremetyevo",55.973648,37.412503));
        setMarkers(listOfAirports, false);

        listOfSights = new LinkedList<>();
        listOfSights.add(new Coordinates("Do Alto Douro",41.10167,-7.79889));
        listOfSights.add(new Coordinates("Museum of Ice Cream",40.739388,-74.009933));
        listOfSights.add(new Coordinates("Los Angeles County Museum of Art",34.064251,-118.360565));
        listOfSights.add(new Coordinates("Royal Observatory Greenwich",51.476852,-0.000500));
        listOfSights.add(new Coordinates("Yellowstone National Park",44.429764,-110.584663));
        listOfSights.add(new Coordinates("Grant Park",41.876465,-87.621887));
        listOfSights.add(new Coordinates("Golden Gate Canyon State Park",39.814339,-105.395622));
        listOfSights.add(new Coordinates("Statue of Liberty National Monument",40.689247,-74.044502));
        listOfSights.add(new Coordinates("Space Needle",47.620422,-122.349358));
        listOfSights.add(new Coordinates("Metropolitan Opera House",40.772713,-73.984337));
        listOfSights.add(new Coordinates("Palace of Versailles",48.804722,2.121782));
        listOfSights.add(new Coordinates("The Eiffel Tower, Paris",48.858093,2.294694));
        listOfSights.add(new Coordinates("Palacio Real",40.417955,-3.714312));
        listOfSights.add(new Coordinates("Aqueduct of Segovia",40.949772,-4.127376));
        listOfSights.add(new Coordinates("Cuenca",40.070392,-2.137416));
        listOfSights.add(new Coordinates("Sagrada Familia",41.403630,2.174356));
        listOfSights.add(new Coordinates("El Escorial",40.589041,-4.147727));
        listOfSights.add(new Coordinates("Mezquita of Cordoba",37.878906,-4.779387));
        listOfSights.add(new Coordinates("Alhambra",37.176078,-3.588141));
        listOfSights.add(new Coordinates("Volkerschlachtdenkmal",51.312367,12.413267));
        listOfSights.add(new Coordinates("Schonbrunn Palace",48.184865,16.312240));
        listOfSights.add(new Coordinates("Great Wall of China",40.431908,116.570375));
        listOfSights.add(new Coordinates("Taj Maha",27.175015,78.042155));
        listOfSights.add(new Coordinates("Palolem",15.009965,74.023219));
        listOfSights.add(new Coordinates("Sydney Opera House",-33.856784,151.215297));
        listOfSights.add(new Coordinates("Uluru/Ayers Rock",-25.344428,131.036882));
        listOfSights.add(new Coordinates("Great Barrier Reef",-18.287067,147.699192));
        listOfSights.add(new Coordinates("Pudong Skyline",31.215657,121.517079));
        listOfSights.add(new Coordinates("Mount Huang",30.113962,118.169798));
        listOfSights.add(new Coordinates("Mount Tai",36.255833,117.105556));
        listOfSights.add(new Coordinates("Hagia Sophia",41.008583,28.980175));
        listOfSights.add(new Coordinates("Goreme Fairy Chimneys",38.640076,34.825514));
        listOfSights.add(new Coordinates("Mount Nemrut",37.980779,38.740799));
        listOfSights.add(new Coordinates("Bialowieza Forest",52.722885,23.655567));
        listOfSights.add(new Coordinates("Warsaw Old Market Place ",52.249968,21.011948));
        listOfSights.add(new Coordinates("Main Market Square",50.061897,19.936756));
        listOfSights.add(new Coordinates("Charles Bridge",50.086477,14.411437));
        listOfSights.add(new Coordinates("Old Town Square",50.087569,14.421187));
        listOfSights.add(new Coordinates("Grote Mark",51.208826,3.224372));
        listOfSights.add(new Coordinates("Tournai Cathedral",50.606492,3.388880));
        listOfSights.add(new Coordinates("Grand Place",50.846732,4.352414));
        listOfSights.add(new Coordinates("Subotica",46.100547,19.665059));

        //setMarkers(listOfSights, true);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(listOfAirports.getFirst().place));


        }
    }

