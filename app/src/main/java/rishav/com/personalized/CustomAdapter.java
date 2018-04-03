package rishav.com.personalized;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rishav.com.personalized.R;

/**
 * Created by rishavg on 4/4/18.
 */

public class CustomAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList wdesc;
    ArrayList  dept;
    ArrayList  alloted;
    ArrayList  priority;
    ArrayList  status;
    Context context;
    CustomAdapter(Context context, ArrayList wdesc, ArrayList dept, ArrayList alloted, ArrayList priortity, ArrayList status)
    {
        this.context=context;
        this.wdesc=wdesc;
        this.dept=dept;
        this.alloted=alloted;
        this.priority=priortity;
        this.status=status;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return wdesc.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.custom_layout,null);
        TextView tv=(TextView)convertView.findViewById(R.id.wdesc);
        TextView tv2=(TextView)convertView.findViewById(R.id.dept);
        TextView tv3=(TextView)convertView.findViewById(R.id.alloted);
        TextView tv4=(TextView)convertView.findViewById(R.id.priority);
        TextView tv5=(TextView)convertView.findViewById(R.id.status);
        tv.setText(wdesc.get(position)+"");
        tv2.setText(dept.get(position)+"");
        tv3.setText(alloted.get(position)+"");
        tv4.setText(priority.get(position)+"");
        tv5.setText(status.get(position)+"");
        return convertView;
    }
}
