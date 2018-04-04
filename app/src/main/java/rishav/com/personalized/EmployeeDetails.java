package rishav.com.personalized;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EmployeeDetails extends AppCompatActivity {

    TextView username,sdesg,ldesg;
    private DatabaseReference mDatabase;
    String posts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        username=(TextView)findViewById(R.id.textView98);
        sdesg=(TextView)findViewById(R.id.textView99);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        posts=ShowOnlyEmployees.emp.get(ShowOnlyEmployees.pos)+"";
        mDatabase.child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map post=(Map)dataSnapshot.getValue();
                //Toast.makeText(Main2Activity.this,post+"",Toast.LENGTH_SHORT).show();
                Set<Map.Entry> s=post.entrySet();
                //Toast.makeText(Main2Activity.this,s+"",Toast.LENGTH_SHORT).show();
                int p1=0;
                for(Map.Entry e:s)
                {
                    HashMap hm=(HashMap) e.getValue();
                    Set<Map.Entry> s1=hm.entrySet();
                    //Toast.makeText(Main2Activity.this,s1+"",Toast.LENGTH_SHORT).show();
                    p1=0;
                    for(Map.Entry e1:s1)
                    {
                        if(e1.getKey().equals("username") && e1.getValue().equals(ShowOnlyEmployees.name))
                        {
                            username.setText(e1.getValue()+"");
                            p1++;
                        }
                    }
                    if(p1==1)
                    {
                        HashMap hm1=(HashMap) e.getValue();
                        Set<Map.Entry> s2=hm.entrySet();
                        for(Map.Entry e2:s2)
                        {
                            if(e2.getKey().equals("sdesig"))
                            {
                                sdesg.setText(e2.getValue()+"");
                            }

                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
