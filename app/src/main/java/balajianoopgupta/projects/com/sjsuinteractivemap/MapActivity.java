package balajianoopgupta.projects.com.sjsuinteractivemap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;

public class MapActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    public final static String BUILDING="balajianoopgupta.projects.com.sjsuinteractivemap";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        View myMap = (View) findViewById(R.id.map);
        myMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String text = "You click at x = " + event.getX() + " and y = " + event.getY();
                Toast.makeText(MapActivity.this, text, Toast.LENGTH_LONG).show();
                float xValue = event.getX();
                float yValue = event.getY();
                Log.i("Coordinates","X:"+xValue);
                Log.i("Coordinates","Y:"+yValue);

                if( (xValue>=730 && xValue<=940) && (yValue>=590 && yValue<=900)){
                    Toast.makeText(MapActivity.this, "Engineering", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"eng");
                    startActivity(building);
                }
                else if( (xValue>=160 && xValue<=280) && (yValue>=590 && yValue<=830)){
                    Toast.makeText(MapActivity.this, "Kings Library", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"king");
                    startActivity(building);
                }
                else if( (xValue>=1150 && xValue<=1300) && (yValue>=1080 && yValue<=1230)){
                    Toast.makeText(MapActivity.this, "BBC", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"bbc");
                    startActivity(building);
                }
                else if( (xValue>=135 && xValue<=275) && (yValue>=1225 && yValue<=1425)){
                    Toast.makeText(MapActivity.this, "Yoshihiro", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"yoshihiro");
                    startActivity(building);
                }
                else if( (xValue>=723 && xValue<=915) && (yValue>=950 && yValue<=1060)){
                    Toast.makeText(MapActivity.this, "Student Union", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"union");
                    startActivity(building);
                }
                else if( (xValue>=115 && xValue<=180) && (yValue>=1540 && yValue<=1950)){
                    Toast.makeText(MapActivity.this, "South Parking", Toast.LENGTH_SHORT).show();
                    Intent building = new Intent(MapActivity.this,BuildingDetailActivity.class);
                    building.putExtra(BUILDING,"parking");
                    startActivity(building);
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
