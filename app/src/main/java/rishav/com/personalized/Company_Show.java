package rishav.com.personalized;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Company_Show extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lview;
    ListViewAdapter lviewAdapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__show);
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        lview = (ListView) findViewById(R.id.listView2);
        Cursor c=db.rawQuery("select * from companylogin",null);
        ArrayList ar=new ArrayList();
        ArrayList ar1=new ArrayList();
        while(c.moveToNext())
        {
            ar.add(c.getString(0));
            ar1.add(c.getString(1));
        }
        lviewAdapter = new ListViewAdapter(this, ar, ar1);
        lview.setAdapter(lviewAdapter);

        lview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,"Title => "+month[position]+"=> n Description"+number[position], Toast.LENGTH_SHORT).show();
        Intent i=new Intent(Company_Show.this,StudentCV.class);
        startActivity(i);
    }
}
