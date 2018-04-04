package rishav.com.personalized;

import android.content.Intent;
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

public class HrSelectEmployee extends AppCompatActivity {

    ListView lv;
    static ArrayList emp;
    static int pos;
    static String name;
    String n,identification,identi;
    ArrayAdapter adapter;
    int count=0;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_select_employee);
        lv=(ListView)findViewById(R.id.lv1);
        emp=new ArrayList();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                        if(e1.getKey().equals("username") && hm.get("sdesig").equals("Employee")) {
                            emp.add(e1.getValue());
                            name=e1.getValue()+"";
                        }
                    }
                }
                adapter = new ArrayAdapter<String>(HrSelectEmployee.this, R.layout.activity_listview, emp);
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                n=emp.get(position)+"";
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
                                if(e1.getKey().equals("username")&&e1.getValue().equals(n) && hm.get("sdesig").equals("Employee")) {

                                    identification=e.getKey()+"";
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
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
                                String des=HrAllotWork.al.get(HrAllotWork.temp)+"";
                                if(e1.getKey().equals("workdesc") && e1.getValue().equals(des))
                                {
                                    identi=e.getKey()+"";
                                    writeNewUser(identi,""+e1.getValue(),hm.get("department")+"",identification,hm.get("proiority")+"","pending");
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Toast.makeText(HrSelectEmployee.this,"Work Allocated",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(HrSelectEmployee.this,HrHead.class);
                startActivity(i);
            }
        });
    }
    private void writeNewUser(String workId, String workdesc, String department, String allotted, String proiority, String status) {
        ChiefAllotWork.User3 user = new ChiefAllotWork.User3(workdesc,department,allotted, proiority,status);

        mDatabase.child("works").child(workId).setValue(user);

    }
}
