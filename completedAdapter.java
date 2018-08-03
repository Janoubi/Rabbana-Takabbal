package hackathon_g043.hajjcontrol;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class completedAdapter extends ArrayAdapter<completed> {

    public completedAdapter(Activity context, ArrayList<completed> completed) {
        super(context, 0, completed);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_layout2, parent, false); //list_layout.xml is mentioned here
        }

        completed e3 = getItem(position);

        TextView tv1=(TextView) listItemView.findViewById(R.id.a3malTitle);
        TextView tv2=(TextView) listItemView.findViewById(R.id.completion);
//        TextView tv3=(TextView) listItemView.findViewById(R.id.magnitude);

        tv1.setText(e3.getmTitle());
        tv2.setText(e3.getmPercent());
//        tv3.setText(Double.toString(ea.getmMagnitude()));


        return listItemView;
    }
}
