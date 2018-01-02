package attendancetracker.ase.com.iot_roomtracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import attendancetracker.ase.com.iot_roomtracker.dao.RoomDeatils;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        SeekBar.OnSeekBarChangeListener,GoogleMap.OnIndoorStateChangeListener, GoogleMap.OnCircleClickListener {

    private GoogleMap mMap;
    private List<RoomDeatils> roomDeatilsList = new ArrayList<RoomDeatils>();
    private static List<Circle> mappedCircle = new ArrayList<Circle>();

    private SeekBar mIndoorSelector;
    private TextView mIndoorMinLevel;
    private TextView mIndoorMaxLevel;
    private android.app.Fragment roomDetailsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mIndoorSelector = (SeekBar) findViewById( R.id.indoor_level_selector );
        mIndoorMinLevel = (TextView) findViewById( R.id.indoor_min_level );
        mIndoorMaxLevel = (TextView) findViewById( R.id.indoor_max_level );

        getRoomDetails();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initMapIndoorSelector();
        LatLng location = new LatLng(41.701921, -86.237693);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,20f));

        IndoorBuilding mIndoorBuilding = mMap.getFocusedBuilding();
        if( mIndoorBuilding == null || mIndoorBuilding.getLevels() == null || mIndoorBuilding.getLevels().size() <= 1 ) {
            hideFloorLevelSelector();
        } else {
            showFloorLevelSelector();
        }
        mapRooms();
    }
    private void hideFloorLevelSelector() {
        mIndoorSelector.setVisibility( View.GONE );
        mIndoorMaxLevel.setVisibility( View.GONE );
        mIndoorMinLevel.setVisibility( View.GONE );
    }

    private void showFloorLevelSelector() {
        IndoorBuilding mIndoorBuilding = mMap.getFocusedBuilding();
        if( mIndoorBuilding == null )
            return;

        int numOfLevels = mIndoorBuilding.getLevels().size();

        mIndoorSelector.setMax( numOfLevels - 1 );

        //Bottom floor is the last item in the list, top floor is the first
        mIndoorMaxLevel.setText( mIndoorBuilding.getLevels().get( 0 ).getShortName() );
        mIndoorMinLevel.setText( mIndoorBuilding.getLevels().get( numOfLevels - 1 ).getShortName() );

        mIndoorSelector.setProgress( mIndoorBuilding.getActiveLevelIndex() );

        mIndoorSelector.setVisibility( View.VISIBLE );
        mIndoorMaxLevel.setVisibility( View.VISIBLE );
        mIndoorMinLevel.setVisibility( View.VISIBLE );

    }
    private void initMapIndoorSelector() {
        mIndoorSelector.setOnSeekBarChangeListener( this );

        mMap.getUiSettings().setIndoorLevelPickerEnabled( false );
        mMap.setOnIndoorStateChangeListener( this );
        mMap.setOnCircleClickListener(this);
    }

    private void getRoomDetails() {
        RoomDeatils r1 = new RoomDeatils(41.701762, -86.237595,true,"McNeill Room",2);
        RoomDeatils r2 = new RoomDeatils(41.701812, -86.237922,false,"Sorin Room",2);
        RoomDeatils r3 = new RoomDeatils(41.701839, -86.237621,false,"Conference Room",0);
        RoomDeatils r4 = new RoomDeatils(41.702102, -86.237642,true,"Conference Room",0);

        roomDeatilsList.add(r1);
        roomDeatilsList.add(r2);
        roomDeatilsList.add(r3);
        roomDeatilsList.add(r4);
    }

    public void mapRooms()
    {
        int color;
        for(Circle circle : mappedCircle)
        {
            circle.remove();
        }
        mMap.clear();
        mappedCircle.clear();
        if(mMap.getFocusedBuilding() != null) {
            for (RoomDeatils room : roomDeatilsList) {
                if (mMap.getFocusedBuilding().getActiveLevelIndex() == room.getFloor()) {
                    if (room.isOccupied())
                        color = Color.RED;
                    else
                        color = Color.GREEN;
                    Circle currentCirc = mMap.addCircle(new CircleOptions().center(new LatLng(room.getLatitude(), room.getLongitude())).radius(0.5).fillColor(color));
                    currentCirc.setTag(room);
                    currentCirc.setClickable(true);
                    mappedCircle.add( currentCirc);

                }
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        IndoorBuilding mIndoorBuilding = mMap.getFocusedBuilding();

        if( mIndoorBuilding == null || seekBar.getVisibility() == View.GONE  )
            return;

        int numOfLevels = mIndoorBuilding.getLevels().size();

        mIndoorBuilding.getLevels().get( numOfLevels - 1 - i ).activate();
        mapRooms();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onIndoorBuildingFocused() {
        IndoorBuilding mIndoorBuilding = mMap.getFocusedBuilding();

        if( mIndoorBuilding == null || mIndoorBuilding.getLevels() == null || mIndoorBuilding.getLevels().size() <= 1 ) {
            hideFloorLevelSelector();
        } else {
            showFloorLevelSelector();
        }
    }

    @Override
    public void onIndoorLevelActivated(IndoorBuilding indoorBuilding) {
        if( indoorBuilding == null )
            return;
    }

    @Override
    public void onCircleClick(Circle circle) {
        Intent intent = new Intent(MapsActivity.this,RoomDetailsActivity.class);
        intent.putExtra("roomDetails",new Gson().toJson(circle.getTag()));
        startActivity(intent);
    }
}
