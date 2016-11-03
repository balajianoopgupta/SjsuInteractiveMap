package balajianoopgupta.projects.com.sjsuinteractivemap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MapActivity extends AppCompatActivity implements  LocationListener,SearchView.OnQueryTextListener {

    private ArrayAdapter<String> adapter;
    public final static String LOCATION = "balajianoopgupta.projects.com.sjsuinteractivemap";
    LocationManager locationManager;
    String provider;
    Location location;
    ArrayList<String> removalList;
    ListView lv;
    String[] arrayBuildings;
    View myMap;

    static FrameLayout frame;
    static DrawBuilding engineeringBuilding;
    static DrawBuilding kingLibrary;
    static DrawBuilding yoshiroHall;
    static DrawBuilding studentUnion;
    static DrawBuilding bbc;
    static DrawBuilding southParking;
    static DrawBuilding currentLocation;

    static float top, bottom, left, right;
    double imageSize;
    Location upperLeft,  upperRight, lowerLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCustomDrawableView = new CustomDrawableView(this);
        setContentView(R.layout.activity_map);

        upperLeft = new Location("");
        upperRight = new Location("");
        lowerLeft = new Location("");

        //King Library
        upperLeft.setLatitude(37.335822);
        upperLeft.setLongitude(-121.885976);

        //Northern Side of University
        upperRight.setLatitude(37.338789);
        upperRight.setLongitude(-121.879744);


        //calc_xy(595.0, target, upperLeft, upperRight);
        lowerLeft.setLatitude(37.331579);
        lowerLeft.setLongitude(-121.882820);



        setContentView(R.layout.activity_map);


        lv = (ListView) findViewById(R.id.listViewBuildings);

        frame = (FrameLayout) findViewById(R.id.activity_map);

        myMap = (View) findViewById(R.id.map);

        //displayScreenValues();

        //1. Initialize the locationManager and the provider
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false); //To return only enabled providers

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);

        if(location != null){
            onLocationChanged(location);
        }

        Log.i("Location","Lat: "+location.getLatitude());
        Log.i("Location","Lng: "+location.getLongitude());

        myMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                top = myMap.getTop();
                bottom = myMap.getBottom();
                left = myMap.getLeft();
                right = myMap.getRight();

                Log.i("Image:","Top: "+top);
                Log.i("Image:","Bottom: "+bottom);
                Log.i("Image:","Left: "+left);
                Log.i("Image:","Right: "+right);

                float xValue = event.getX();
                float yValue = event.getY();
                Log.i("Coordinates","X:"+xValue);
                Log.i("Coordinates","Y:"+yValue);

                Log.i("Coordinates","New X:" +xValue/right);
                Log.i("Coordinates","New Y:" +yValue/bottom);

                Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                Bundle data = new Bundle();
                float[] arr = new float[2];
                arr[0] = (float) location.getLatitude();
                arr[1] = (float) location.getLongitude();
                data.putFloatArray("Location",arr);
                building.putExtra(LOCATION,location);

//                if( (xValue>=730 && xValue<=940) && (yValue>=590 && yValue<=900)){
//                    Toast.makeText(MapActivity.this, "Engineering", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","eng");
//                }
                if((xValue>=(0.50*right) && xValue<=(0.66*right)) && (yValue>=(0.34*bottom) && yValue<=(0.45*bottom))){
                    Toast.makeText(MapActivity.this, "Engineering", Toast.LENGTH_SHORT).show();
                    data.putString("Building","eng");
                }
//                else if( (xValue>=160 && xValue<=280) && (yValue>=590 && yValue<=830)){
//                    Toast.makeText(MapActivity.this, "Kings Library", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","king");
//                }
                else if((xValue>=(0.10*right) && xValue<=(0.2*right)) && (yValue>=(0.34*bottom) && yValue<=(0.425*bottom))){
                    Toast.makeText(MapActivity.this, "Kings Library", Toast.LENGTH_SHORT).show();
                    data.putString("Building","king");
                }
//                else if( (xValue>=1150 && xValue<=1300) && (yValue>=1080 && yValue<=1230)){
//                    Toast.makeText(MapActivity.this, "BBC", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","bbc");
//                }
                else if((xValue>=(0.80*right) && xValue<=(0.90*right)) && (yValue>=(0.51*bottom) && yValue<=(0.56*bottom))){
                    Toast.makeText(MapActivity.this, "BBC", Toast.LENGTH_SHORT).show();
                    data.putString("Building","bbc");
                }
//                else if( (xValue>=135 && xValue<=275) && (yValue>=1225 && yValue<=1425)){
//                    Toast.makeText(MapActivity.this, "Yoshihiro", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","yoshihiro");
//                }
                else if((xValue>=(0.09*right) && xValue<=(0.20*right)) && (yValue>=(0.56*bottom) && yValue<=(0.63*bottom))){
                    Toast.makeText(MapActivity.this, "Yoshihiro", Toast.LENGTH_SHORT).show();
                    data.putString("Building","yoshihiro");
                }
//                else if( (xValue>=723 && xValue<=915) && (yValue>=950 && yValue<=1060)){
//                    Toast.makeText(MapActivity.this, "Student Union", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","union");
//                }
                else if((xValue>=(0.49*right) && xValue<=(0.63*right)) && (yValue>=(0.45*bottom) && yValue<=(0.5*bottom))){
                    Toast.makeText(MapActivity.this, "Student Union", Toast.LENGTH_SHORT).show();
                    data.putString("Building","union");
                }
//                else if( (xValue>=430 && xValue<=700) && (yValue>=1710 && yValue<=1950)){
//                    Toast.makeText(MapActivity.this, "South Parking", Toast.LENGTH_SHORT).show();
//                    data.putString("Building","parking");
//                }
                else if((xValue>=(0.3*right) && xValue<=(0.48*right)) && (yValue>=(0.73*bottom) && yValue<=(0.81*bottom))){
                    Toast.makeText(MapActivity.this, "South Parking", Toast.LENGTH_SHORT).show();
                    data.putString("Building","parking");
                }
                else{
                    //Do Nothing when clicked outside any of the boundaries mentioned above
                    return true;
                }

                building.putExtras(data);
                startActivity(building);
                return true;
            }
        });


        final ImageButton locationButton = (ImageButton) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                provider = locationManager.getBestProvider(new Criteria(), false); //To return only enabled providers

                if (ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(provider);

                if(location != null){
                    onLocationChanged(location);
                }

                Log.i("Location","Lat: "+location.getLatitude());
                Log.i("Location","Lng: "+location.getLongitude());

                top = myMap.getTop();
                bottom = myMap.getBottom();
                left = myMap.getLeft();
                right = myMap.getRight();

                removeMarkings();

//                double a1 = calculateDistance(37.335822, -121.885976, location.getLatitude(), location.getLongitude());
//                double a2 = calculateDistance(37.331579, -121.882820, location.getLatitude(), location.getLongitude());
//                double King_West = calculateDistance(37.335822, -121.885976, 37.331579, -121.882820 );
//                double King_North = calculateDistance(37.335822, -121.885976, 37.338789, -121.879744);
//                double newY = (Math.pow(a2,2)+Math.pow(0.3,2)-Math.pow(a1,2))/(2*0.3);

                double a1 = calculateDistance(upperLeft.getLatitude(),upperLeft.getLongitude(), location.getLatitude(), location.getLongitude());
                double a2 = calculateDistance(lowerLeft.getLatitude(),lowerLeft.getLongitude(), location.getLatitude(), location.getLongitude());
                double King_West = calculateDistance(upperLeft.getLatitude(),upperLeft.getLongitude(), lowerLeft.getLatitude(),lowerLeft.getLongitude() );
                double King_North = calculateDistance(upperLeft.getLatitude(),upperLeft.getLongitude(), upperRight.getLatitude(),upperRight.getLongitude());

                double newY = (Math.pow(a2,2)+Math.pow(0.3,2)-Math.pow(a1,2))/(2*0.3);
                Log.i("new Y","newY:"+newY);

                double myX = Math.sqrt(Math.pow(a2,2)-Math.pow(newY,2));
                Log.i("X","myX:"+myX);
                double myY = King_West - newY;

                double xPixels = (0.03*right) + (myX * 0.9 * right)/King_North ;
                double yPixels = (0.32*bottom) + (myY * 0.5 * bottom)/King_West;

                Log.i("Distance","xPixels: "+xPixels);
                Log.i("Distance","yPixels: "+yPixels);

                currentLocation = new DrawBuilding(MapActivity.this, (float)xPixels, (float)yPixels);
                frame.addView(currentLocation);

            }
        });
    }

    public double calculateDistance(double x1,double y1,double x2,double y2){

        double lat1Radians = Math.toRadians(x1);
        double lat2Radians = Math.toRadians(x2);
        double diff1 = y2-y1;
        double diff2 = Math.toRadians(diff1);
        double myValue = Math.sin(lat1Radians) * Math.sin(lat2Radians) + Math.cos(lat1Radians) * Math.cos(lat2Radians) * Math.cos(diff2);
        myValue = Math.acos(myValue);
        myValue = Math.toDegrees(myValue);
        myValue = myValue * 60 * 1.1515;

        return myValue;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView =(SearchView) menu.findItem(R.id.menuSearch).getActionView();

        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    private void handleItems() {
        top = myMap.getTop();
        bottom = myMap.getBottom();
        left = myMap.getLeft();
        right = myMap.getRight();

        kingLibrary = new DrawBuilding(MapActivity.this, (float)(0.09*right), (float)(0.34*bottom), (float)(0.2*right), (float)(0.43*bottom));

        engineeringBuilding = new DrawBuilding(MapActivity.this, (float)(0.51*right), (float)(0.34*bottom), (float)(0.66*right), (float)(0.45*bottom));

        yoshiroHall = new DrawBuilding(MapActivity.this, (float)(0.09*right), (float)(0.56*bottom), (float)(0.2*right), (float)(0.63*bottom));

        bbc = new DrawBuilding(MapActivity.this, (float)(0.8*right), (float)(0.51*bottom), (float)(0.9*right), (float)(0.56*bottom));

        studentUnion = new DrawBuilding(MapActivity.this, (float)(0.51*right), (float)(0.46*bottom), (float)(0.64*right), (float)(0.5*bottom));

        southParking = new DrawBuilding(MapActivity.this, (float)(0.30*right), (float)(0.73*bottom), (float)(0.48*right), (float)(0.81*bottom));

        //Creating All the 6 buildings that we are searching
        arrayBuildings = new String[]{"King Library","Engineering Building","Yoshihiro Uchida Hall","Student Union","BBC","South Parking Garage"};

        //Copying all the buildings into removalList array and removing items when matched in search string
        removalList = new ArrayList<>(Arrays.asList(arrayBuildings));

        adapter = new ArrayAdapter<String>(MapActivity.this,android.R.layout.simple_list_item_1,removalList);

        lv.setAdapter(adapter);
        lv.setVisibility(View.INVISIBLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getItemAtPosition(position);
                //Toast.makeText(MapActivity.this, "Option Selected: "+item, Toast.LENGTH_LONG).show();

                removeMarkings();
                displayBuilding(item);

                lv.setVisibility(View.INVISIBLE);

                //Hide keypad after selecting an option in the listView
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    public static void removeMarkings() {

        //((FrameLayout)frame.getParent()).removeView(kingLibrary);
        frame.removeView(kingLibrary);
        frame.removeView(engineeringBuilding);
        frame.removeView(yoshiroHall);
        frame.removeView(southParking);
        frame.removeView(bbc);
        frame.removeView(studentUnion);
    }

    private void displayBuilding(String item) {
        removeMarkings();

        if(item.toLowerCase().equals(arrayBuildings[0].toLowerCase())){
            drawRectangle(kingLibrary);
        }
        else if(item.toLowerCase().equals(arrayBuildings[1].toLowerCase())){
            drawRectangle(engineeringBuilding);
        }
        else if(item.toLowerCase().equals(arrayBuildings[2].toLowerCase())){
            drawRectangle(yoshiroHall);
        }
        else if(item.toLowerCase().equals(arrayBuildings[3].toLowerCase())){
            drawRectangle(studentUnion);
        }
        else if(item.toLowerCase().equals(arrayBuildings[4].toLowerCase())){
            drawRectangle(bbc);
        }
        else{
            drawRectangle(southParking);
        }

    }

    private void drawRectangle(DrawBuilding building) {
        frame.addView(building);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(newText.isEmpty()){
            handleItems();
            removeMarkings();
        }
        else {

            for (String str: arrayBuildings) {
                if(!(str.toLowerCase().contains(newText.toLowerCase()))){
                    removalList.remove(str);
                }
            }
            lv.setVisibility(View.VISIBLE);
            lv.bringToFront();
            adapter.notifyDataSetChanged();
        }

        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("Status","In onLocationChanged");

        //2. Get the current location
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Status", "Latitude is: "+ lat.toString());
        Log.i("Status", "Longitude is: "+ lng.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void displayScreenValues(){

        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+
        Log.i("Screen", "displayName  = " + displayName);

        // display size in pixels
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i("Screen", "width        = " + width);
        Log.i("Screen", "height       = " + height);

        // pixels, dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        Log.i("Screen", "widthPixels  = " + widthPixels);
        Log.i("Screen", "heightPixels = " + heightPixels);
        Log.i("Screen", "densityDpi   = " + densityDpi);
        Log.i("Screen", "xdpi         = " + xdpi);
        Log.i("Screen", "ydpi         = " + ydpi);

        // deprecated
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();
        Log.i("Screen", "screenHeight = " + screenHeight);
        Log.i("Screen", "screenWidth  = " + screenWidth);

        // orientation (either ORIENTATION_LANDSCAPE, ORIENTATION_PORTRAIT)
        int orientation = getResources().getConfiguration().orientation;
        Log.i("Screen", "orientation  = " + orientation);
    }
}
