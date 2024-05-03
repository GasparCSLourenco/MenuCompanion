package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseTable extends AppCompatActivity {

    //All the tables in the restaurant
    Button table1,table2,table3,table4,table5,table6,scan;

    //New User that will be created
    User user;
    //DB
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);

        //Getting table references
        table1 = findViewById(R.id.table1);
        table2 = findViewById(R.id.table2);
        table3 = findViewById(R.id.table3);
        table4 = findViewById(R.id.table4);
        table5 = findViewById(R.id.table5);
        table6 = findViewById(R.id.table6);
        scan = findViewById(R.id.buttonScan);

        //Adding click listener to all table buttons
        table1.setOnClickListener(this::onClickTable);
        table2.setOnClickListener(this::onClickTable);
        table3.setOnClickListener(this::onClickTable);
        table4.setOnClickListener(this::onClickTable);
        table5.setOnClickListener(this::onClickTable);
        table6.setOnClickListener(this::onClickTable);

        //Getting the user from previous activity
        Bundle userData = getIntent().getExtras();
        //Passing data to new user object
        user = (User)userData.getSerializable("user");
        //Instantiating DB
        dbHandler = new DBHandler(ChooseTable.this);



    }

    public void onClickTable(View v){

        int id = v.getId();
        Intent menu = new Intent(getApplicationContext(), MainMenu.class);
        //Setting the user's table to the one clicked by the user
        if     (id == R.id.table1){user.SetTable(1);}
        else if(id == R.id.table2){user.SetTable(2);}
        else if(id == R.id.table3){user.SetTable(3);}
        else if(id == R.id.table4){user.SetTable(4);}
        else if(id == R.id.table5){user.SetTable(5);}
        else if(id == R.id.table6){user.SetTable(6);}

        //passing the user to the new activity
        menu.putExtra("user",user);
        //Adding the user to the DB;
        dbHandler.AddUser(user);
        startActivity(menu);
    }
}