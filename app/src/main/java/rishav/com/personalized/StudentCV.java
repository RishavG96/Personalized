package rishav.com.personalized;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class StudentCV extends AppCompatActivity {

    EditText et,name,age,cv,email;
    Calendar myCalendar;
    RadioButton male,female;
    Button ok;
    String ctr="S1";
    int count=0;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_cv);
        et = (EditText)findViewById(R.id.editText2);
        ok=(Button)findViewById(R.id.button2);
        name=(EditText)findViewById(R.id.editText);
        age=(EditText)findViewById(R.id.editText3);
        cv=(EditText)findViewById(R.id.editText4);
        email=(EditText)findViewById(R.id.editText14);
        male=(RadioButton)findViewById(R.id.radioButton);
        female=(RadioButton)findViewById(R.id.radioButton2);
        myCalendar= Calendar.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n=name.getText()+"";
                String a=age.getText()+"";
                String dob=et.getText()+"";
                String g="";
                if(male.isChecked())
                    g=male.getText()+"";
                else
                    g=female.getText()+"";
                String c=cv.getText()+"";
                String e=email.getText()+"";
                mDatabase.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map post=(Map)dataSnapshot.getValue();
                        Toast.makeText(StudentCV.this,post+"",Toast.LENGTH_SHORT).show();
                        Set s=post.keySet();
                        Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                        count=0;
                        for(Object o:s)
                        {
                            Toast.makeText(StudentCV.this,s+"",Toast.LENGTH_SHORT).show();
                            String temp=((String)o).substring(1);
                            count=Integer.parseInt(temp);
                        }
                        count++;
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                writeNewUser("S"+count,n+"", a+"",dob+"",g+"",c+"",e+"");
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(StudentCV.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void writeNewUser(String studentId, String name, String age, String dob, String gender, String cv, String email) {
        User user = new User(name,age,dob,gender,cv, email);

        mDatabase.child("students").child(studentId).setValue(user);

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et.setText(sdf.format(myCalendar.getTime()));
    }
    @IgnoreExtraProperties
    public static class User {

        public String username;
        public String age;
        public String dob;
        public String gender;
        public String cv;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username,String age,String dob,String gender,String cv, String email) {
            this.username = username;
            this.age=age;
            this.dob=dob;
            this.gender=gender;
            this.cv=cv;
            this.email = email;
        }

    }
}
