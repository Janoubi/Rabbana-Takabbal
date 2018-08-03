package hackathon_g043.hajjcontrol;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MngrAdapter extends ArrayAdapter<WorkFlow> {

    public MngrAdapter(Activity context, ArrayList<WorkFlow> workflow)
    {
        super(context,0,workflow);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_layout2, parent, false); //list_layout.xml is mentioned here
        }

        WorkFlow ea=getItem(position);

//        TextView tv1=(TextView) listItemView.findViewById(R.id.location);
//        TextView tv2=(TextView) listItemView.findViewById(R.id.time);
//        TextView tv3=(TextView) listItemView.findViewById(R.id.magnitude);

//        tv1.setText(ea.getmLocation());
//        tv2.setText(Long.toString(ea.getmTime()));
//        tv3.setText(Double.toString(ea.getmMagnitude()));

        return listItemView;
    }

}
