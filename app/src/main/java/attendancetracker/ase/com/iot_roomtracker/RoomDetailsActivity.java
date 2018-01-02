package attendancetracker.ase.com.iot_roomtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import attendancetracker.ase.com.iot_roomtracker.dao.RoomDeatils;

public class RoomDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        String jsonMyObject = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("roomDetails");
        }
        RoomDeatils myObject = new Gson().fromJson(jsonMyObject, RoomDeatils.class);
        TextView t = (TextView) findViewById(R.id.textView2);
        t.setText(myObject.getRoomName());
    }
}
