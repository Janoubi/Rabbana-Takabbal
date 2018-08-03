package hackathon_g043.hajjcontrol;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
private DatabaseReference mDatabase;
private DatabaseReference mGPS;
private SharedPreferences sp;
private String spId;
private String spCode;
private String spType;

//request GPS
LocationManager locationManager;
LocationListener locationListener;

private Button btA3mal;
private Button btCompanyInfo;
private Button btConfirmedA3mal;
private Button btCompanyHajjDistributions;
private Button btAreaStatistics;
private Button btHajjDistribution;
private ImageView imLogo;
private Button btEnter;
EditText edUserID; //Primary Key
EditText edCode;  //provided by the App Company
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }
//

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    sp=this.getSharedPreferences("hackathon_g043.hajjcontrol", Context.MODE_PRIVATE);
    loadsp();
    createViews();
    target(spType);


btA3mal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent=new Intent(MainActivity.this,HajjActivity.class);
        startActivity(myIntent);
    }
});
btConfirmedA3mal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent=new Intent(MainActivity.this,CompletedActivity.class);
        startActivity(myIntent);
    }
});
btCompanyHajjDistributions.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent=new Intent(MainActivity.this,MapsActivity2.class);
        startActivity(myIntent);
    }
});
btHajjDistribution.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent=new Intent(MainActivity.this,MapsActivity2.class);
        startActivity(myIntent);
    }
});

btAreaStatistics.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent myIntent=new Intent(MainActivity.this,MapsActivity.class);
        startActivity(myIntent);
    }
});

btEnter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(!edCode.getText().toString().isEmpty() && !edUserID.getText().toString().isEmpty()){
            spId=edUserID.getText().toString();
            spCode=edCode.getText().toString();
mGPS=getReference("Hajjis",spId,"location").getRef();
        checkDatabase();
    }
    }
});


    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(spType.equals("Hajj")) {

                long UnixTime = Long.valueOf(System.currentTimeMillis() / 1000);  //in seconds
                mGPS = getReference("Hajjis", spId, "location");
                mGPS.child("0").setValue(location.getLatitude());//Latitude
                mGPS.child("1").setValue(location.getLongitude());//Longitude
                mGPS.child("2").setValue(UnixTime);//Time
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    if (Build.VERSION.SDK_INT < 23) {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    } else {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//            mMap.clear();
//            LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        }
    }

    }



    private DatabaseReference getReference(String group,String ... childs )
    {
        DatabaseReference dr;
        dr= FirebaseDatabase.getInstance().getReference(group);

        for(String a:childs)
        {
         dr=dr.child(a);
        }

        return dr.getRef();

    }


    private void loadsp(){
        spId=sp.getString("ID","");
       spCode=sp.getString("CODE","");
      spType=sp.getString("TYPE","");

    }

    private void savesp(){

        sp.edit().putString("ID",spId).apply();
        sp.edit().putString("CODE",spCode).apply();
        sp.edit().putString("TYPE",spType).apply();

    }

//    private void addListener (DatabaseReference dr)
//    {
//        dr.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }



    private void checkDatabase()
    {

        mDatabase=getReference("Hajjis",spId,"type");
    mDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            spType=String.valueOf(dataSnapshot.getValue());
            savesp();
            target(spType);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }
    public void toast(String text)
    {
        Toast.makeText(this, text,
                Toast.LENGTH_SHORT).show();
    }
private void target(String type)
{


    switch (type) {
        case "Hajj":
            hideall();
            mDatabase=getReference("Hajjis",spId,"location");
showHajj();
            break;

        case "Gover":
            hideall();
            mDatabase=getReference("Hajjis",spId,"location");
showGover();
            break;

        case "Mngr":
            hideall();
            mDatabase=getReference("Hajjis",spId,"location");
showMngr();
            break;
        default:
            hideall();
            showRegister();

    }
}

private void hideall(){//should be completed
btA3mal.setVisibility(View.GONE);
btCompanyInfo.setVisibility(View.GONE);
btConfirmedA3mal.setVisibility(View.GONE);
btCompanyHajjDistributions.setVisibility(View.GONE);
btAreaStatistics.setVisibility(View.GONE);
btHajjDistribution.setVisibility(View.GONE);
edCode.setVisibility(View.GONE);
edUserID.setVisibility(View.GONE);
btEnter.setVisibility(View.GONE);
}

private void showRegister(){
    edCode.setVisibility(View.VISIBLE);
    edUserID.setVisibility(View.VISIBLE);
    imLogo.setVisibility(View.VISIBLE);
    btEnter.setVisibility(View.VISIBLE);
}
private void showMngr(){
    btConfirmedA3mal.setVisibility(View.VISIBLE);
    btCompanyHajjDistributions.setVisibility(View.VISIBLE);
}
private void showGover(){
    btAreaStatistics.setVisibility(View.VISIBLE);
    btHajjDistribution.setVisibility(View.VISIBLE);
}
private void showHajj(){
    btA3mal.setVisibility(View.VISIBLE);
    btCompanyInfo.setVisibility(View.VISIBLE);

}
public void log(String input){
    Log.e("bluee",input);
}

private void createViews(){
    btA3mal=(Button) findViewById(R.id.a3mal);
    btCompanyInfo=(Button) findViewById(R.id.companyInfo);
    btConfirmedA3mal=(Button) findViewById(R.id.confirmedA3mal);
    btCompanyHajjDistributions=(Button) findViewById(R.id.companyHajjDistribution);
    btAreaStatistics=(Button) findViewById(R.id.areaStatistics);
    btHajjDistribution=(Button) findViewById(R.id.hajjDistribution);
    edCode=(EditText) findViewById(R.id.code);
    edUserID=(EditText) findViewById(R.id.userID);
    imLogo=(ImageView) findViewById(R.id.logo);
    btEnter=(Button) findViewById(R.id.enter);

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
