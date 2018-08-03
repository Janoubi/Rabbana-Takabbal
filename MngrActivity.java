package hackathon_g043.hajjcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;

public class MngrActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private DatabaseReference mGPS;
    private SharedPreferences sp;
    private String spId;
    private String spCode;
    private String spType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr);
        sp=this.getSharedPreferences("hackathon_g043.hajjcontrol", Context.MODE_PRIVATE);
        loadsp();
    }
    private void loadsp(){
        spId=sp.getString("ID","");
        spCode=sp.getString("CODE","");
        spType=sp.getString("TYPE","");

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
