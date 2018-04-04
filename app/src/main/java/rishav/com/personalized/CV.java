package rishav.com.personalized;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CV extends AppCompatActivity {

    ListView lv;
    ArrayList al;
    SQLiteDatabase db;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);
        lv=(ListView)findViewById(R.id.lv5);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        al=new ArrayList();
        mDatabase.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
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
                        if(e1.getKey().equals("username") )
                        {
                            al.add(e1.getValue());
                        }

                    }
                    ArrayAdapter adapter=new ArrayAdapter(CV.this, android.R.layout.simple_list_item_1,al);
                    lv.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
