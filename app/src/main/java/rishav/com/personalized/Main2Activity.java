package rishav.com.personalized;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {

    Button login,signup,submit;
    EditText un1,pass1,compname,compdesc,un2,pass2,pass3;
    //SQLiteDatabase db;
    int count=0,flag;
    String u,p;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        login=(Button)findViewById(R.id.button3);
        signup=(Button)findViewById(R.id.button4);
        submit=(Button)findViewById(R.id.button5);
        un1=(EditText)findViewById(R.id.editText);
        pass1=(EditText)findViewById(R.id.editText2);
        compname=(EditText)findViewById(R.id.editText5);
        compdesc=(EditText)findViewById(R.id.editText6);
        un2=(EditText)findViewById(R.id.editText7);
        pass2=(EditText)findViewById(R.id.editText8);
        pass3=(EditText)findViewById(R.id.editText3);
        compname.setVisibility(View.INVISIBLE);
        compdesc.setVisibility(View.INVISIBLE);
        un2.setVisibility(View.INVISIBLE);
        pass2.setVisibility(View.INVISIBLE);
        pass3.setVisibility(View.INVISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un1.setVisibility(View.VISIBLE);
                pass1.setVisibility(View.VISIBLE);
                compname.setVisibility(View.INVISIBLE);
                compdesc.setVisibility(View.INVISIBLE);
                un2.setVisibility(View.INVISIBLE);
                pass2.setVisibility(View.INVISIBLE);
                pass3.setVisibility(View.INVISIBLE);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                un1.setVisibility(View.INVISIBLE);
                pass1.setVisibility(View.INVISIBLE);
                compname.setVisibility(View.VISIBLE);
                compdesc.setVisibility(View.VISIBLE);
                un2.setVisibility(View.VISIBLE);
                pass2.setVisibility(View.VISIBLE);
                pass3.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(un1.getVisibility()==View.INVISIBLE)
                {
                    //signup
                    String cn=compname.getText()+"";
                    String cd=compdesc.getText()+"";
                    String un=un2.getText()+"";
                    String pass;
                    if((pass2.getText()+"").equals(pass3.getText()+"")){
                        pass=pass2.getText()+"";
                    }
                    else {
                        pass = "Error";
                    }
                    if(!pass.equals("Error"))
                    {
                        //writeNewUser("C1",cn+"",cd+"",un+"",pass+"");
                        mDatabase.child("companies").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map post=(Map)dataSnapshot.getValue();
                                //Toast.makeText(StudentCV.this,post+"",Toast.LENGTH_SHORT).show();
                                Set s=post.keySet();
                                //Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                                count=0;
                                for(Object o:s)
                                {
                                    //Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                                    String temp=((String)o).substring(1);
                                    count=Integer.parseInt(temp);
                                }
                                count++;
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        writeNewUser("C"+count,cn+"",cd+"",un+"",pass+"");
                            un1.setVisibility(View.VISIBLE);
                            pass1.setVisibility(View.VISIBLE);
                            compname.setVisibility(View.INVISIBLE);
                            compdesc.setVisibility(View.INVISIBLE);
                            un2.setVisibility(View.INVISIBLE);
                            pass2.setVisibility(View.INVISIBLE);
                            pass3.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(Main2Activity.this,"Password is different", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //login
                    u=un1.getText()+"";
                    p=pass1.getText()+"";
                    //Toast.makeText(Main2Activity.this,u+"",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Main2Activity.this,p+"",Toast.LENGTH_SHORT).show();
                    flag=0;
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
                                for(Map.Entry e1:s1)
                                {
                                    if(e1.getKey().equals("username") && e1.getValue().equals(u+""))
                                    {
                                        p1++;
                                    }
                                    if(e1.getKey().equals("pass") && e1.getValue().equals(p+""))
                                    {
                                        p1++;
                                    }
                                }
                            }
                            //Toast.makeText(Main2Activity.this,p1+"",Toast.LENGTH_SHORT).show();
                            if(p1==2){
                                    //Toast.makeText(Main2Activity.this,"Hello",Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(Main2Activity.this,"Correct",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(Main2Activity.this,Main3Activity.class);
                                    startActivity(i);
                                }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

            }
        });
    }
    private void writeNewUser(String compId, String cname, String cdesc, String username, String pass) {
        User1 user = new User1(cname,cdesc,username, pass);

        mDatabase.child("companies").child(compId).setValue(user);

    }
    @IgnoreExtraProperties
    public static class User1 {

        public String cname;
        public String cdesc;
        public String username;
        public String pass;

        public User1() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User1(String cname,String cdesc,String username, String pass) {
            this.username = username;
            this.cname=cname;
            this.cdesc=cdesc;
            this.pass = pass;
        }

    }
}
