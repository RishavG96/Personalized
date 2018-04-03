package rishav.com.personalized;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class ShowAllEmployees extends AppCompatActivity {

    ArrayList emp;
    ArrayAdapter adapter;
    ListView listView;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_employees);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.mobile_list);
        emp=new ArrayList();
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
                    for(Map.Entry e1:s1)
                    {
                        if(e1.getKey().equals("desig"))
                        {
                            emp.add(e1.getValue());
                        }
                    }
                }
                adapter = new ArrayAdapter<String>(ShowAllEmployees.this, R.layout.activity_listview, emp);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}