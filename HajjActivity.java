package hackathon_g043.hajjcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HajjActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String spId;
    private String spCode;
    private String spType;
    HajjAdapter mAdapter;
    ListView HajjlistView;
    ArrayList<A3mal> A3malItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hajj);
        A3malItems=new ArrayList<A3mal>();

        mAdapter = new HajjAdapter(this,A3malItems);
        sp=this.getSharedPreferences("hackathon_g043.hajjcontrol", Context.MODE_PRIVATE);
        loadsp();
        HajjlistView = (ListView) findViewById(R.id.listA3mal);
        HajjlistView.setAdapter(mAdapter);




        mDatabase= FirebaseDatabase.getInstance().getReference("Hajjis").child(spId).child("works").getRef();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                A3malItems.clear();
String title="";
boolean status=false;
String location="";  //location is ID
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String post = postSnapshot.getKey();
                    for (DataSnapshot postsnapshot2 : postSnapshot.getChildren()) {
                        String post1=postsnapshot2.getKey();

                        if(post1.equals("status")){
                            status=postsnapshot2.getValue(boolean.class);    //status

                        }
                        if(post1.equals("title")){
title=postsnapshot2.getValue().toString();       //title
                        }




                    }
                    Log.e("bluee","post"+post);
                    addItem(title,status,post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //tvr=(TextView) findViewById(R.id.empty_view) ;
       // earthquakeListView.setEmptyView(tvr);  //To auto visible the empty Textview when it is an emptylist
        // Create a new adapter that takes an empty list of earthquakes as input

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

    }

    private void loadsp(){
        spId=sp.getString("ID","");
        spCode=sp.getString("CODE","");
        spType=sp.getString("TYPE","");

    }

    private void addItem(String title,boolean status,String location){
        A3malItems.add(new A3mal(title,status,location,spId));
mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){

            case R.id.exit:
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.mainMenu))
                        .setMessage(getString(R.string.mainMenuConfirmation))
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp.edit().clear().apply();
                            }
                        })
                        .setNegativeButton(getString(R.string.no),null)
                        .show();

                return true;

        }
        return false;
    }
}
