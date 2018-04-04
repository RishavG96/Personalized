package rishav.com.personalized;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Employee extends AppCompatActivity {

    ListView lv;
    static int temp;
    static ArrayList al;
    AlertDialog.Builder adb;
    String i;
    String n,identification,identi;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        lv=(ListView)findViewById(R.id.lv3);
        registerForContextMenu(lv);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        al=new ArrayList();
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
                        if(e1.getKey().equals("alotted") && e1.getValue().equals(Main3Activity.empid) && hm.get("status").equals("pending"))
                        {
                            al.add(hm.get("workdesc")+"");
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
        inflater.inflate(R.menu.emp_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        i=al.get(listPosition)+"";
        if(item.getTitle().equals("Finish"))
        {
            temp=listPosition;
            adb=new AlertDialog.Builder(this);
            adb.setTitle("Are you sure?");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
                                    if(e1.getKey().equals("workdesc"))
                                    {
                                        identi=e.getKey()+"";
                                        writeNewUser(identi,""+e1.getValue(),hm.get("department")+"",Main3Activity.empid,hm.get("proiority")+"","finished");
                                        al=new ArrayList();
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
                                                        if(e1.getKey().equals("alotted") && e1.getValue().equals(Main3Activity.empid) && hm.get("status").equals("pending"))
                                                        {
                                                            al.add(hm.get("workdesc")+"");
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
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    Toast.makeText(Employee.this, ""+i,Toast.LENGTH_SHORT).show();
                }
            });
            adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Employee.this, "Cancelled",Toast.LENGTH_SHORT).show();
                }
            });
            adb.show();
        }
        return true;
    }
    private void writeNewUser(String workId, String workdesc, String department, String allotted, String proiority, String status) {
        ChiefAllotWork.User3 user = new ChiefAllotWork.User3(workdesc,department,allotted, proiority,status);

        mDatabase.child("works").child(workId).setValue(user);

    }
}
