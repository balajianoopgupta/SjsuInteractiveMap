package balajianoopgupta.projects.com.sjsuinteractivemap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BuildingDetailActivity extends AppCompatActivity {
    BuildingDetails[] buildings = new BuildingDetails[6];
    TextView buildingName,buildingAddress;
    static TextView travelDistance;
    ImageView buildingImage;
    float[] location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_detail);

        setBuildings();

        buildingName = (TextView)findViewById(R.id.buildingName);
        buildingAddress = (TextView) findViewById(R.id.buildingAddress);
        travelDistance = (TextView) findViewById(R.id.buildingDistance);
        buildingImage = (ImageView) findViewById(R.id.buildingImage);

        Intent i = getIntent();
        String building = i.getExtras().getString("Building");
        location = i.getExtras().getFloatArray("Location");
        //new GetLocation().execute(String.valueOf(location[0]),String.valueOf(location[1]));
        //new GetLocation().execute();
        Log.i("building",String.valueOf(building));

        switch (building){
            case "king": Log.i("building","Switch KING");
                displayBuildingDetails(buildings[0]);
                break;
            case "eng":  Log.i("building","Switch ENG");
                displayBuildingDetails(buildings[1]);
                break;
            case "yoshihiro": Log.i("building","Switch YOHSIHIRO");
                displayBuildingDetails(buildings[2]);
                break;
            case "union": Log.i("building","Switch STUD_UNION");
                displayBuildingDetails(buildings[3]);
                break;
            case "bbc": Log.i("building","Switch BBC");
                displayBuildingDetails(buildings[4]);
                break;
            case "parking": Log.i("building","Switch SOUTH_PARKING");
                displayBuildingDetails(buildings[5]);
                break;
        }

    }

    public void setBuildings(){
        buildings[0] = new BuildingDetails("King Library","Dr. Martin Luther King, Jr. Library","150 East San Fernando Street, San Jose, CA 95112", "balajianoopgupta.projects.com.sjsuinteractivemap:drawable/king", 37.335592, -121.884913);
        buildings[1] = new BuildingDetails("Engineering Building","San José State University Charles W. Davidson College of Engineering","1 Washington Square, San Jose, CA 95112", "balajianoopgupta.projects.com.sjsuinteractivemap:drawable/eng", 37.337061, -121.881515);
        buildings[2] = new BuildingDetails("Yoshihiro Uchida Hall","Yoshihiro Uchida Hall","Yoshihiro Uchida Hall, San Jose, CA 95112", "balajianoopgupta.projects.com.sjsuinteractivemap:drawable/yoshihiro", 37.337061, -121.881515);
        buildings[3] = new BuildingDetails("Student Union","Student Union","Student Union Building, San Jose, CA 95112","balajianoopgupta.projects.com.sjsuinteractivemap:drawable/studentunion",37.336523, -121.881255);
        buildings[4] = new BuildingDetails("BBC","Boccardo Business Complex","Boccardo Business Complex, San Jose, CA 95112","balajianoopgupta.projects.com.sjsuinteractivemap:drawable/bbc",37.337044, -121.878896);
        buildings[5] = new BuildingDetails("South Parking Garage","San Jose State University South Garage","330 South 7th Street, San Jose, CA 95112","balajianoopgupta.projects.com.sjsuinteractivemap:drawable/southparking",37.333616, -121.880146);
    }

    public void displayBuildingDetails(BuildingDetails value){
        JSONObject out = null;
        buildingName.setText((CharSequence)value.getName());
        buildingAddress.setText("Address: "+(CharSequence)value.getAddress());
        int imgId = getResources().getIdentifier(value.photo,null,null);
        buildingImage.setImageResource(imgId);
        try{
            new GetLocation().execute(String.valueOf(location[0]),String.valueOf(location[1]),String.valueOf(value.getLat()), String.valueOf(value.getLng())).get();
            //Log.i("Sample",out.toString());
        }
        catch (InterruptedException e){

        }
        catch (ExecutionException e) {

        }
    }

    public static android.os.Handler receiver = new android.os.Handler(){
        public void handleMessage(Message msg){
            Log.i("FInally",String.valueOf(msg.what));
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(msg.obj));
                String time = jsonObject.getJSONObject("duration").getString("text");
                String distance = jsonObject.getJSONObject("distance").getString("text");
                //travelDistance.setText(String.valueOf(distance));
                travelDistance.setText("Travel distance: "+String.valueOf(distance)+", Travel time: "+String.valueOf(time));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };
}
