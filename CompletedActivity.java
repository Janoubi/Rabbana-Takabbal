package hackathon_g043.hajjcontrol;

import android.app.Activity;
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
import android.widget.ListView;

import com.google.android.gms.common.api.BooleanResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
completedAdapter mAdapter;

    private DatabaseReference mGPS;
    private SharedPreferences sp;
    private String spId;
    private String spCode;
    private String spType;
    ListView HajjlistView;
    ArrayList<completed> A3malItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        A3malItems=new ArrayList<completed>();

        mAdapter = new completedAdapter(this,A3malItems);
        sp=this.getSharedPreferences("hackathon_g043.hajjcontrol", Context.MODE_PRIVATE);
        loadsp();

        mDatabase= FirebaseDatabase.getInstance().getReference("Hajjis").getRef();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a=0;
                        int all=0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String post = postSnapshot.getKey();
                    for (DataSnapshot postSnapshot2 : postSnapshot.getChildren()) {
                        if(postSnapshot2.getKey().equals("works"))
                        {

                            for (DataSnapshot postSnapshot3 : postSnapshot2.getChildren()){//at 0 and 1
                               Log.e("bluee",postSnapshot3.getKey());
                                for (DataSnapshot postSnapshot4 : postSnapshot3.getChildren()){
                                    if(postSnapshot3.getKey().equals("0")){
                                        if(postSnapshot4.getKey().equals("status")){
                                            if(Boolean.valueOf(postSnapshot4.getValue().toString())){
                                                a++;
                                            }

                                        }
                                    }
                                }
                            }

                        }




                    }
                   // Log.e("bluee","post"+post);
                    //addItem(title,status,post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadsp(){
        spId=sp.getString("ID","");
        spCode=sp.getString("CODE","");
        spType=sp.getString("TYPE","");

    }

    private void addItem(String title,String percent){
        A3malItems.add(new completed(title,percent));
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
