package rishav.com.personalized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BoardMembers extends AppCompatActivity {

    TextView tv;
    Button trackprogress,allotwork,showemps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_members);
        allotwork=(Button)findViewById(R.id.button10);
        showemps=(Button)findViewById(R.id.button11);
        tv=(TextView)findViewById(R.id.textView8);
        tv.setText("Welcome "+Main3Activity.name);
        allotwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BoardMembers.this, ChiefAllotWork.class);
                startActivity(i);
            }
        });
        showemps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BoardMembers.this, ShowAllEmployees.class);
                startActivity(i);
            }
        });
    }
}
