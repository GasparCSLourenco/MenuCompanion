package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnEn,btnPT,btnAbout;
    DBHandler dbHandler;



    Seeding seeding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //English and Portuguese buttons for future localization
        btnEn = findViewById(R.id.buttonEn);
        btnPT = findViewById(R.id.buttonPt);
        btnAbout = findViewById(R.id.buttonAbout);
        seeding = new Seeding();

        //Instantiating the DB when first opened;
        dbHandler = new DBHandler(MainActivity.this);

        if(dbHandler.ReadDishes().size() == 0){
            dbHandler.AddDish(seeding.SeedDB());
        }


        btnEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserForm.class);
                startActivity(intent);
            }
        });

        btnPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Feature not implemented yet.",Toast.LENGTH_SHORT).show();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent about = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(about);
            }
        });




    }
}