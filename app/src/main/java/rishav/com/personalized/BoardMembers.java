package rishav.com.personalized;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BoardMembers extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_members);
        tv=(TextView)findViewById(R.id.textView8);
        tv.setText("Welcome "+Main3Activity.name);
    }
}
