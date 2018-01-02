package attendancetracker.ase.com.iot_roomtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private String userName;
    private String password;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = getIntent();
        userName = intent.getStringExtra("userName");
        password = intent.getStringExtra("password");
        Button button = (Button) findViewById(R.id.loginBtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(userAuthenticated(userName,password))
                {
                    intent = new Intent(LoginActivity.this,MapsActivity.class);
                    startActivity(intent);
                }
                else
                {
                    TextView textViewToChange = (TextView) findViewById(R.id.invalidLogin);
                    textViewToChange.setText("Invalid User ID or Password.");
                }
            }
        });
    }

    private boolean userAuthenticated(String userName, String password)
    {
        return true;
    }

}
