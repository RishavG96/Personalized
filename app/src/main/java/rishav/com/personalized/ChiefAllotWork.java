package rishav.com.personalized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Set;

public class ChiefAllotWork extends AppCompatActivity {

    EditText wdesc;
    Spinner sp;
    RatingBar rb;
    Button submit;
    String wd,dept,p;
    private DatabaseReference mDatabase;
    int count=0;
    String d[]={"Finance", "Human Resources","Marketing","RnD"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_allot_work);
        wdesc=(EditText)findViewById(R.id.editText);
        sp=(Spinner)findViewById(R.id.spinner3);
        rb=(RatingBar)findViewById(R.id.ratingBar);
        rb.setMax(5);
        submit=(Button)findViewById(R.id.button13);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,d);
        sp.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wd=wdesc.getText()+"";
                dept=sp.getSelectedItem()+"";
                p=String.valueOf(rb.getRating());
                mDatabase.child("works").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map post=(Map)dataSnapshot.getValue();
                        Toast.makeText(ChiefAllotWork.this,post+"",Toast.LENGTH_SHORT).show();
                        Set s=post.keySet();
                        //Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                        count=0;
                        for(Object o:s)
                        {
                            //Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                            String temp=((String)o).substring(1);
                            int t=Integer.parseInt(temp);
                            if(t>count)
                                count=t;
                        }
                        count++;
                        writeNewUser("W"+count,wd+"",dept+"","head",p+"","pending");
                        Toast.makeText(ChiefAllotWork.this,"Task acknowledged",Toast.LENGTH_SHORT).show();
                        wdesc.setText("");
                        sp.setSelection(0);
                        rb.setRating(0);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
    private void writeNewUser(String workId, String workdesc, String department, String allotted, String proiority, String status) {
        User3 user = new User3(workdesc,department,allotted, proiority,status);

        mDatabase.child("works").child(workId).setValue(user);

    }
    @IgnoreExtraProperties
    public static class User3 {

        public String workdesc;
        public String department;
        public String alotted;
        public String proiority;
        public String status;

        public User3() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User3(String workdesc,String department,String alotted, String proiority,String status) {
            this.workdesc = workdesc;
            this.department=department;
            this.alotted=alotted;
            this.proiority= proiority;
            this.status=status;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.works_menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i=new Intent(ChiefAllotWork.this,ShowWorks.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
