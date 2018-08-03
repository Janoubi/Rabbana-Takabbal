package hackathon_g043.hajjcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {
    private DatabaseReference mDatabase;

    private DatabaseReference mGPS;
    private SharedPreferences sp;
    private String spId;
    private String spCode;
    private String spType;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        sp=this.getSharedPreferences("hackathon_g043.hajjcontrol", Context.MODE_PRIVATE);
        loadsp();

        mGPS= FirebaseDatabase.getInstance().getReference("Hajjis").getRef();

        mGPS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){//numbers
                    double[] doubleArray=new double[3];
                    String name="";
                    for (DataSnapshot postSnapshot1: postSnapshot.getChildren()){//userchildren

                            if(postSnapshot1.getKey().equals("location")) {//userlocation
                                int i=0;
                                for (DataSnapshot postSnapshot2: postSnapshot1.getChildren()) {
                                    doubleArray[i]=Double.valueOf(postSnapshot2.getValue().toString());
                                    Log.e("bluee", String.valueOf(doubleArray[i]));
                                    i++;
                                }
                            }
                        if(postSnapshot1.getKey().equals("name")){
                                name=postSnapshot1.getValue().toString();
                            Log.e("bluee", "name:"+name);

                        }


                    }
                    addMarker(name,doubleArray[0],doubleArray[1]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng makkah = new LatLng(21.422510, 39.826168);
        LatLng arafat = new LatLng(21.3666652, 40.00166666);
        mMap.addMarker(new MarkerOptions().position(makkah).title("Marker in Makkah"));
        mMap.addMarker(new MarkerOptions().position(arafat).title("Marker in Arafat"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arafat,12));

    }
    private void loadsp(){
        spId=sp.getString("ID","");
        spCode=sp.getString("CODE","");
        spType=sp.getString("TYPE","");

    }


    private void addMarker(String name,double latitude,double longitude)
    {
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title(name));

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
