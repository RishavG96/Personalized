package rishav.com.personalized;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button login,signup,submit;
    EditText un1,pass1,compname,compdesc,un2,pass2,pass3;
    SQLiteDatabase db;
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
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        if(db!=null)
        {
            try
            {
                db.execSQL("create table companylogin(cmpname varchar(50), cmpdetails varchar(100), username varchar(50), password varchar(25))");
            }
            catch (Exception e)
            {
                Toast.makeText(Main2Activity.this,"Error here",Toast.LENGTH_SHORT).show();
            }
        }
        un1.setVisibility(View.INVISIBLE);
        pass1.setVisibility(View.INVISIBLE);
        compname.setVisibility(View.INVISIBLE);
        compdesc.setVisibility(View.INVISIBLE);
        un2.setVisibility(View.INVISIBLE);
        pass2.setVisibility(View.INVISIBLE);
        pass3.setVisibility(View.INVISIBLE);
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
                        db.execSQL("insert into companylogin values ( '"+cn+"', '"+cd+"', '"+un+"', '"+pass+"' )");
                    }
                    else{
                        Toast.makeText(Main2Activity.this,"PAssword is different", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    //login

                }
            }
        });
    }
}
