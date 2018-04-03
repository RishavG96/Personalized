package rishav.com.personalized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShowWorks extends AppCompatActivity {

    ListView lv;
    ArrayList workdes,dept,alloted,priority,status;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_works);
        lv=(ListView)findViewById(R.id.listView3);
        workdes=new ArrayList();
        dept=new ArrayList();
        alloted=new ArrayList();
        priority=new ArrayList();
        status=new ArrayList();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("works").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map post=(Map)dataSnapshot.getValue();
                //Toast.makeText(Main2Activity.this,post+"",Toast.LENGTH_SHORT).show();
                Set<Map.Entry> s=post.entrySet();
                //Toast.makeText(Main2Activity.this,s+"",Toast.LENGTH_SHORT).show();
                for(Map.Entry e:s)
                {
                    HashMap hm=(HashMap) e.getValue();
                    Set<Map.Entry> s1=hm.entrySet();
                    for(Map.Entry e1:s1)
                    {
                        if(e1.getKey().equals("workdesc") )
                        {
                            workdes.add(e1.getValue()+"");
                        }
                        if(e1.getKey().equals("department") )
                        {
                            dept.add(e1.getValue()+"");
                        }
                        if(e1.getKey().equals("alotted") )
                        {
                            alloted.add(e1.getValue()+"");
                        }
                        if(e1.getKey().equals("proiority") )
                        {
                            priority.add(e1.getValue()+"");
                        }
                        if(e1.getKey().equals("status") )
                        {
                            status.add(e1.getValue()+"");
                        }
                    }
                }
                //Toast.makeText(ShowWorks.this,workdes+"% "+dept, Toast.LENGTH_SHORT).show();
                CustomAdapter adapter=new CustomAdapter(getApplicationContext(),workdes,dept,alloted,priority,status);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
