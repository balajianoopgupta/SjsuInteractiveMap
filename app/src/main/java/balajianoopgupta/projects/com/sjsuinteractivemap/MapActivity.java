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
    Location target, upperLeft,  upperRight, lowerRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCustomDrawableView = new CustomDrawableView(this);
        target = new Location("");
        upperLeft = new Location("");
        upperRight = new Location("");
        lowerRight = new Location("");
        upperLeft.setLatitude(37.335822);
        upperLeft.setLongitude(-121.886025);
        upperRight.setLatitude(37.338751);
        upperRight.setLongitude(-121.879703);
        lowerRight.setLatitude(37.334556);
        lowerRight.setLongitude(-121.876701);
//        target.setLatitude(37.335894);
//        target.setLongitude(-121.882672);


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


        target.setLatitude(location.getLatitude());
        target.setLongitude(location.getLongitude());

        Log.i("Target","Lat:"+target.getLatitude());
        Log.i("Target","Lng:"+target.getLongitude());
        //37.334556, -121.876701


        double [] values = new double[2];
        values = calc_xy(1400, target, upperLeft, upperRight);
        //values = test(target, upperLeft, upperRight);
        final double xValue =  values[0];
        final double yValue =  values[1];
        final double x = getCurrentPixelX(upperLeft, lowerRight, target) + 144;
        final double y = getCurrentPixelY(upperLeft, lowerRight, target) + 1208;

        final ImageButton locationButton = (ImageButton) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                top = myMap.getTop();
                bottom = myMap.getBottom();
                left = myMap.getLeft();
                right = myMap.getRight();

                double xPix = x;//- (0.03*right);
                //getCurrentPixelX(upperLeft, lowerRight, location);
                double yPix = y; //- (0.31 * bottom); //getCurrentPixelY(upperLeft, lowerRight, location);

                currentLocation = new DrawBuilding(MapActivity.this, (float)xPix, (float)yPix);
                frame.addView(currentLocation);
            }
        });
    }

    public double getCurrentPixelY(Location upperLeft, Location lowerRight, Location current) {
        double hypotenuse = upperLeft.distanceTo(current);
        double bearing = upperLeft.bearingTo(current);
        double currentDistanceY = Math.cos(bearing * Math.PI / 180.0d) * hypotenuse;
        //                           "percentage to mark the position"
        double totalHypotenuse = upperLeft.distanceTo(lowerRight);
        double totalDistanceY = totalHypotenuse * Math.cos(upperLeft.bearingTo(lowerRight) * Math.PI / 180.0d);
        double currentPixelY = currentDistanceY / totalDistanceY * 2108;

        return currentPixelY;
    }
    public double getCurrentPixelX(Location upperLeft, Location lowerRight, Location current) {
        double hypotenuse = upperLeft.distanceTo(current);
        double bearing = upperLeft.bearingTo(current);
        double currentDistanceX = Math.sin(bearing * Math.PI / 180.0d) * hypotenuse;
        //                           "percentage to mark the position"
        double totalHypotenuse = upperLeft.distanceTo(lowerRight);
        double totalDistanceX = totalHypotenuse * Math.sin(upperLeft.bearingTo(lowerRight) * Math.PI / 180.0d);
        double currentPixelX = currentDistanceX / totalDistanceX * 1440;

        return currentPixelX;
    }
    public static double[] calc_xy (double imageSize, Location target, Location upperLeft, Location upperRight) {
        double newAngle = -1;
        try {
            double angle = calc_radian(upperRight.getLongitude(), upperRight.getLatitude(),
                    upperLeft.getLongitude(), upperLeft.getLatitude(),
                    target.getLongitude(), target.getLatitude());
            newAngle = 180-angle;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        double upperLeft_Target_dist = upperLeft.distanceTo(target);

        double upperLeft_Right_dist = upperLeft.distanceTo(upperRight);
        double distancePerPx = imageSize /upperLeft_Right_dist;

        double distance = upperLeft_Target_dist * distancePerPx;

        double radian = newAngle * Math.PI/180;

        double[] result = radToPixel(distance, radian);
        return result;
    }

    public static double[] radToPixel(double distance, double radian) {
        double[] result = {-1,-1};
        result[0] = distance * Math.cos(radian) + 25;//(0.03*right);
        result[1] = distance * Math.sin(radian) + 175;//(0.31*bottom);
        return result;
    }

    public static double calc_radian(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3)
            throws Exception{

        double rad = 0.0;

        if((Double.compare(x1, x2) == 0 && Double.compare(y1, y2) == 0) ||
                (Double.compare(x3, x2) == 0 && Double.compare(y3, y2) == 0))
        {
            return rad;
        }

    /* compute vector */
        double BAx = x2 - x1;
        double BAy = y2 - y1;

        double BCx = x3 - x2;
        double BCy = y3 - y2;

        double cosA =  BAx / Math.sqrt( BAx * BAx + BAy * BAy ) ;
        double cosC =  BCx / Math.sqrt( BCx * BCx + BCy * BCy ) ;

        double radA = Math.acos( cosA ) * 180.0 / Math.PI ;
        double radC = Math.acos( cosC ) * 180.0 / Math.PI ;
        if( BAy < 0.0 )
        {
            radA = radA * -1.0 ;
        }
        if( BCy < 0.0 )
        {
            radC = radC * -1.0 ;
        }

        rad = radC - radA ;


        if( rad > 180.0 )
        {
            rad = rad - 360;
        }

        if( rad < -180.0 )
        {
            rad = rad + 360;
        }

        return rad ;
    }
    //
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

//        Log.i("Image:","Top: "+top);
//        Log.i("Image:","Bottom: "+bottom);
//        Log.i("Image:","Left: "+left);
//        Log.i("Image:","Right: "+right);

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
                Toast.makeText(MapActivity.this, "Option Selected: "+item, Toast.LENGTH_LONG).show();

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
