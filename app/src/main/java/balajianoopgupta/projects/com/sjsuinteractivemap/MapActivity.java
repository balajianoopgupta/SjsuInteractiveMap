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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class MapActivity extends AppCompatActivity implements  LocationListener {

    private ArrayAdapter<String> adapter;
    public final static String BUILDING = "balajianoopgupta.projects.com.sjsuinteractivemap";
    public final static String LOCATION = "balajianoopgupta.projects.com.sjsuinteractivemap";
    LocationManager locationManager;
    String provider;
    Location location;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

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



        View myMap = (View) findViewById(R.id.map);
        myMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String text = "You click at x = " + event.getX() + " and y = " + event.getY();
                //Toast.makeText(MapActivity.this, text, Toast.LENGTH_LONG).show();
                float xValue = event.getX();
                float yValue = event.getY();
                Log.i("Coordinates","X:"+xValue);
                Log.i("Coordinates","Y:"+yValue);

                Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                Bundle data = new Bundle();
                float[] arr = new float[2];
                arr[0] = (float) location.getLatitude();
                arr[1] = (float) location.getLongitude();
                data.putFloatArray("Location",arr);
                building.putExtra(LOCATION,location);

                if( (xValue>=730 && xValue<=940) && (yValue>=590 && yValue<=900)){
                    Toast.makeText(MapActivity.this, "Engineering", Toast.LENGTH_SHORT).show();
                    data.putString("Building","eng");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"eng");
                }
                else if( (xValue>=160 && xValue<=280) && (yValue>=590 && yValue<=830)){
                    Toast.makeText(MapActivity.this, "Kings Library", Toast.LENGTH_SHORT).show();
                    data.putString("Building","king");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"king");
                }
                else if( (xValue>=1150 && xValue<=1300) && (yValue>=1080 && yValue<=1230)){
                    Toast.makeText(MapActivity.this, "BBC", Toast.LENGTH_SHORT).show();
                    data.putString("Building","bbc");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"bbc");
                }
                else if( (xValue>=135 && xValue<=275) && (yValue>=1225 && yValue<=1425)){
                    Toast.makeText(MapActivity.this, "Yoshihiro", Toast.LENGTH_SHORT).show();
                    data.putString("Building","yoshihiro");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"yoshihiro");
                }
                else if( (xValue>=723 && xValue<=915) && (yValue>=950 && yValue<=1060)){
                    Toast.makeText(MapActivity.this, "Student Union", Toast.LENGTH_SHORT).show();
                    data.putString("Building","union");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"union");
                }
                else if( (xValue>=115 && xValue<=180) && (yValue>=1540 && yValue<=1950)){
                    Toast.makeText(MapActivity.this, "South Parking", Toast.LENGTH_SHORT).show();
                    data.putString("Building","parking");
                    building.putExtras(data);
                    startActivity(building);
                    //building.putExtra(BUILDING,"parking");
                }
                else{
                    //Do Nothing when clicked outside any of the boundaries mentioned above
                }

                return true;
            }
        });

        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        ListView lv = (ListView) findViewById(R.id.listViewBuildings);

        ArrayList<String> arrayBuildings = new ArrayList<>();
        arrayBuildings.addAll(Arrays.asList(getResources().getStringArray(R.array.array_buildings)));

        adapter = new ArrayAdapter<>(
                MapActivity.this,
                android.R.layout.simple_list_item_1,
                arrayBuildings);

        lv.setAdapter(adapter);
    }

    private void searchBuilding(String query) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menuSearch).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //We can do anything to get the search results here
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    public void handleIntent(Intent intent){
        // Get the intent, verify the action and get the query
        intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchBuilding(query);
        }
    }
}
