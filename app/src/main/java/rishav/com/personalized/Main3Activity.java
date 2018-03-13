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

public class Main3Activity extends AppCompatActivity {

    String desig[]={"Select here","Chief Executive Officer","Chief Operating Offier","Chief Financial Officer","Chief Marketing Officer",
            "Chief Technology Officer"};
    TextView tv;
    Spinner sp;
    EditText et1,et2,et3, un,pass;
    Button submit,login,signup;
    SQLiteDatabase db;
    static String name;
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
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);

            try
            {
                db.execSQL("create table IF NOT EXISTS employerlogin(desig varchar(50),  username varchar(50), password varchar(25))");
            }
            catch (Exception e)
            {
                //Toast.makeText(Main2Activity.this,"Error here",Toast.LENGTH_SHORT).show();
            }
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
                    String des = sp.getSelectedItem() + "";
                    String u= et1.getText()+"";
                    String p="";
                    if((et2.getText()+"").equals(et3.getText()+""))
                        p= et2.getText()+"";
                    else
                    {
                        Toast.makeText(Main3Activity.this,"Password do not match",Toast.LENGTH_SHORT).show();
                    }
                    if(!p.equals(""))
                    {
                        Cursor c=db.rawQuery("select * from employerlogin",null);
                        int flag=0;
                        while(c.moveToNext())
                        {
                            if(u.equals(c.getString(1)) && p.equals(c.getString(2)))
                            {
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0){
                            db.execSQL("insert into employerlogin values ( '" + des + "', '" + u+ "', '" + p + "' )");
                            Toast.makeText(Main3Activity.this, "Values Inserted, please Login", Toast.LENGTH_SHORT).show();
                            tv.setVisibility(View.INVISIBLE);
                            sp.setVisibility(View.INVISIBLE);
                            et1.setVisibility(View.INVISIBLE);
                            et2.setVisibility(View.INVISIBLE);
                            et3.setVisibility(View.INVISIBLE);
                            un.setVisibility(View.VISIBLE);
                            pass.setVisibility(View.VISIBLE);
                        }
                    }
                }
                else
                {
                    String u=un.getText()+"";
                    String p=pass.getText()+"";
                    Cursor c=db.rawQuery("select * from employerlogin",null);
                    int flag=0;
                    while(c.moveToNext())
                    {
                        if(u.equals(c.getString(1)) && p.equals(c.getString(2)))
                        {
                            name=c.getString(1);
                            flag=1;
                            break;
                        }
                    }
                    if(flag==1)
                    {
                        //Toast.makeText(Main3Activity.this,"Correct",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(Main3Activity.this,BoardMembers.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
}
