package rishav.com.personalized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HrHead extends AppCompatActivity {

    TextView tv;
    Button trackprogress,allotwork,showemps,students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_head);
        allotwork=(Button)findViewById(R.id.button91);
        showemps=(Button)findViewById(R.id.button92);
        tv=(TextView)findViewById(R.id.textView100);
        students=(Button)findViewById(R.id.button105);
        tv.setText("Welcome "+Main3Activity.name);
        allotwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HrHead.this, HrAllotWork.class);
                startActivity(i);
            }
        });
        showemps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HrHead.this, ShowOnlyEmployees.class);
                startActivity(i);
            }
        });
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HrHead.this, CV.class);
                startActivity(i);
            }
        });
    }
}
