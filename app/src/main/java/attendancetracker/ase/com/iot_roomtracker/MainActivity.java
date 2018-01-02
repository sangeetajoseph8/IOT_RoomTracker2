package attendancetracker.ase.com.iot_roomtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent;
        if(!ifUserLoggedIn())
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    private boolean ifUserLoggedIn()
    {
        return false;
    }
}
