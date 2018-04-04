package rishav.com.personalized;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main3Activity extends AppCompatActivity {

    String desig[]={"Select here","Chief Executive Officer","Chief Operating Offier","Chief Financial Officer","Chief Marketing Officer",
            "Chief Technology Officer"};
    TextView tv;
    Spinner sp;
    EditText et1,et2,et3, un,pass;
    Button submit,login,signup;
    //SQLiteDatabase db;
    private DatabaseReference mDatabase;
    int count=0,flag;
    String u,p,des;
    static String name="",empid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        tv=(TextView)findViewById(R.id.textView);
        sp=(Spinner)findViewById(R.id.spinner);
        et1=(EditText)findViewById(R.id.editText4);
        et2=(EditText)findViewById(R.id.editText9);
        et3=(EditText)findViewById(R.id.editText10);
        un=(EditText)findViewById(R.id.editText11);
        pass=(EditText)findViewById(R.id.editText12);
        submit=(Button)findViewById(R.id.button8);
        login=(Button)findViewById(R.id.button6);
        signup=(Button)findViewById(R.id.button7);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,desig);
        sp.setAdapter(adapter);
        tv.setVisibility(View.INVISIBLE);
        sp.setVisibility(View.INVISIBLE);
        et1.setVisibility(View.INVISIBLE);
        et2.setVisibility(View.INVISIBLE);
        et3.setVisibility(View.INVISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.INVISIBLE);
                sp.setVisibility(View.INVISIBLE);
                et1.setVisibility(View.INVISIBLE);
                et2.setVisibility(View.INVISIBLE);
                et3.setVisibility(View.INVISIBLE);
                un.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setVisibility(View.VISIBLE);
                sp.setVisibility(View.VISIBLE);
                et1.setVisibility(View.VISIBLE);
                et2.setVisibility(View.VISIBLE);
                et3.setVisibility(View.VISIBLE);
                un.setVisibility(View.INVISIBLE);
                pass.setVisibility(View.INVISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv.getVisibility()==View.VISIBLE) {
                    des = sp.getSelectedItem() + "";
                    u= et1.getText()+"";
                    p="";
                    if((et2.getText()+"").equals(et3.getText()+""))
                        p= et2.getText()+"";
                    else
                    {
                        Toast.makeText(Main3Activity.this,"Password do not match",Toast.LENGTH_SHORT).show();
                    }
                    if(!p.equals(""))
                    {
                        mDatabase.child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map post=(Map)dataSnapshot.getValue();
                                //Toast.makeText(StudentCV.this,post+"",Toast.LENGTH_SHORT).show();
                                Set s=post.keySet();
                                Toast.makeText(Main3Activity.this,s+"",Toast.LENGTH_SHORT).show();
                                count=0;
                                for(Object o:s)
                                {
                                    Toast.makeText(Main3Activity.this,o+"",Toast.LENGTH_SHORT).show();
                                    String temp=((String)o).substring(1);
                                    Toast.makeText(Main3Activity.this,temp+"",Toast.LENGTH_SHORT).show();
                                    int t=Integer.parseInt(temp);
                                    if(t>count)
                                        count=t;
                                }
                                count++;
                                String t[]=des.split(" ");
                                String sdes=t[0];
                                writeNewUser("E"+count,Main2Activity.compid,sdes,des+"",u+"",p+"");
                                Toast.makeText(Main3Activity.this, "Values Inserted, please Login", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                            tv.setVisibility(View.INVISIBLE);
                            sp.setVisibility(View.INVISIBLE);
                            et1.setVisibility(View.INVISIBLE);
                            et2.setVisibility(View.INVISIBLE);
                            et3.setVisibility(View.INVISIBLE);
                            un.setVisibility(View.VISIBLE);
                            pass.setVisibility(View.VISIBLE);

                    }
                }
                else
                {
                     u=un.getText()+"";
                     p=pass.getText()+"";

                    flag=0;
                    mDatabase.child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map post=(Map)dataSnapshot.getValue();
                            //Toast.makeText(Main2Activity.this,post+"",Toast.LENGTH_SHORT).show();
                            Set<Map.Entry> s=post.entrySet();
                            //Toast.makeText(Main2Activity.this,s+"",Toast.LENGTH_SHORT).show();
                            int p1=0;
                            int flag=0;
                            for(Map.Entry e:s)
                            {
                                HashMap hm=(HashMap) e.getValue();
                                Set<Map.Entry> s1=hm.entrySet();
                                //Toast.makeText(Main2Activity.this,s1+"",Toast.LENGTH_SHORT).show();
                                p1=0;
                                for(Map.Entry e1:s1)
                                {
                                    if(e1.getKey().equals("username") && e1.getValue().equals(u+""))
                                    {
                                        name=u;
                                        p1++;
                                    }
                                    if(e1.getKey().equals("pass") && e1.getValue().equals(p+""))
                                    {
                                        p1++;
                                    }
                                    if(e1.getKey().equals("sdesig") && e1.getValue().equals("Head"))
                                        flag=1;
                                    if(e1.getKey().equals("sdesig") && e1.getValue().equals("Employee"))
                                        flag=2;
                                }
                                if(p1==2 && flag==0){
                                    empid=e.getKey()+"";
                                    Toast.makeText(Main3Activity.this,""+empid,Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Main3Activity.this,BoardMembers.class);
                                    startActivity(i);
                                }
                                else if(p1==2 && flag==1){
                                    empid=e.getKey()+"";
                                    Toast.makeText(Main3Activity.this,""+empid,Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Main3Activity.this,HrHead.class);
                                    startActivity(i);
                                }else if(p1==2 && flag==2){
                                    empid=e.getKey()+"";
                                    Toast.makeText(Main3Activity.this,""+empid,Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Main3Activity.this,Employee.class);
                                    startActivity(i);
                                }
                            }
                            //Toast.makeText(Main3Activity.this,p1+"",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
    }
    private void writeNewUser(String empId,String compid,String sdesignation, String designation, String username, String pass) {
        User2 user = new User2(compid,sdesignation,designation,username, pass);

        mDatabase.child("employees").child(empId).setValue(user);

    }
    @IgnoreExtraProperties
    public static class User2 {

        public String desig;
        public String sdesig;
        public String username;
        public String pass;
        public String compid;
        public User2() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User2(String compid,String sdesig,String desig,String username, String pass) {
            this.username = username;
            this.compid=compid;
            this.desig=desig;
            this.sdesig=sdesig;
            this.pass = pass;
        }

    }
}
