package rishav.com.personalized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Set;

public class AddEmp extends AppCompatActivity {

    String desig[]={"Select here","Head of HR","Head of RnD","Manager of Marketing",
            "Manager of Finance"};
    Spinner sp;
    EditText username,pass,confirmpass;
    Button b;
    int count=0;
    private DatabaseReference mDatabase;
    String u,p,des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);
        sp=(Spinner)findViewById(R.id.spinner2);
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,desig);
        sp.setAdapter(adapter);
        username=(EditText)findViewById(R.id.editText13);
        pass=(EditText)findViewById(R.id.editText15);
        confirmpass=(EditText)findViewById(R.id.editText16);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        b=(Button)findViewById(R.id.button12);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                des = sp.getSelectedItem() + "";
                u= username.getText()+"";
                p="";
                if((pass.getText()+"").equals(confirmpass.getText()+""))
                    p= pass.getText()+"";
                else
                {
                    Toast.makeText(AddEmp.this,"Password do not match",Toast.LENGTH_SHORT).show();
                }
                if(!p.equals("")) {
                    mDatabase.child("employees").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map post = (Map) dataSnapshot.getValue();
                            //Toast.makeText(StudentCV.this,post+"",Toast.LENGTH_SHORT).show();
                            Set s = post.keySet();
                            count = 0;
                            for (Object o : s) {
                                String temp = ((String) o).substring(1);
                                int t = Integer.parseInt(temp);
                                if (t > count)
                                    count = t;
                            }
                            count++;
                            String t[] = des.split(" ");
                            String sdes = t[0];
                            writeNewUser("E" + count, Main2Activity.compid, sdes, des + "", u + "", p + "");
                            Intent i=new Intent(AddEmp.this,ShowAllEmployees.class);
                            startActivity(i);
                            finish();
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
        Main3Activity.User2 user = new Main3Activity.User2(compid,sdesignation,designation,username, pass);

        mDatabase.child("employees").child(empId).setValue(user);

    }
}
