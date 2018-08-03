package hackathon_g043.hajjcontrol;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HajjAdapter extends ArrayAdapter<A3mal> {
    private DatabaseReference mDatabase;
    private Switch sw1;
    public HajjAdapter(Activity context, ArrayList<A3mal> A3mals)
    {
        super(context,0,A3mals);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_layout1, parent, false); //list_layout.xml is mentioned here
        }

        A3mal e3=getItem(position);

//        TextView tv1=(TextView) listItemView.findViewById(R.id.location);
//        TextView tv2=(TextView) listItemView.findViewById(R.id.time);
//        TextView tv3=(TextView) listItemView.findViewById(R.id.magnitude);

//        tv1.setText(ea.getmLocation());
//        tv2.setText(Long.toString(ea.getmTime()));
//        tv3.setText(Double.toString(ea.getmMagnitude()));

        TextView tv1=(TextView) listItemView.findViewById(R.id.a3malTitle);
        sw1=(Switch) listItemView.findViewById(R.id.switch1);
tv1.setText(e3.getmTitle());
sw1.setChecked(e3.getmStatus());
        mDatabase= FirebaseDatabase.getInstance().getReference("Hajjis").child(e3.getmId()).child("works");

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("bluee","checked:"+String.valueOf(isChecked));
              if(isChecked)
              {
                  mDatabase.child(String.valueOf(position)).child("status").getRef().setValue(true);

              }
              else {
                  mDatabase.child(String.valueOf(position)).child("status").getRef().setValue(false);
              }
            }
        });
        return listItemView;
    }



}
