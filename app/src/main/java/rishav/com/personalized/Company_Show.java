package rishav.com.personalized;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Company_Show extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lview;
    ListViewAdapter lviewAdapter;
    ArrayList al;
    SQLiteDatabase db;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__show);
        lview=(ListView)findViewById(R.id.listView2);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        al=new ArrayList();
        mDatabase.child("companies").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    ArrayAdapter adapter=new ArrayAdapter(Company_Show.this, android.R.layout.simple_list_item_1,al);
                    lview.setAdapter(adapter);
                    lview.setOnItemClickListener(Company_Show.this);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,"Title => "+month[position]+"=> n Description"+number[position], Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Company_Show.this,StudentCV.class);
        startActivity(i);
    }
}
