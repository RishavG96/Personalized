package rishav.com.personalized;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HrAllotWork extends AppCompatActivity {

    ListView lv;
    static int temp;
    static ArrayList al;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_allot_work);
        lv=(ListView)findViewById(R.id.lv);
        registerForContextMenu(lv);
        al=new ArrayList();
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
                        if(e1.getKey().equals("workdesc") && hm.get("department").equals("Human Resources"))
                        {
                            al.add(e1.getValue()+"");
                        }
                    }
                }
                //Toast.makeText(ShowWorks.this,workdes+"% "+dept, Toast.LENGTH_SHORT).show();
                ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,al);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.head_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        String i=al.get(listPosition)+"";
        if(item.getTitle().equals("Allocate"))
        {
            temp=listPosition;
            Intent i1=new Intent(HrAllotWork.this,HrSelectEmployee.class);
            startActivity(i1);
            //Toast.makeText(Main2Activity.this, ""+i,Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
