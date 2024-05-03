package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

public class ManageMenu extends AppCompatActivity {

    private DBHandler dbHandler;
    private RVAdapterMenu rvAdapterMenu;

    private ArrayList<Dish> dishList;

    private RecyclerView rvManageMenu;
    Button btnFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_menu);

        dbHandler = new DBHandler(ManageMenu.this);

        dishList = dbHandler.ReadDishes();

        rvAdapterMenu = new RVAdapterMenu(ManageMenu.this, dishList);

        rvManageMenu= findViewById(R.id.recyclerViewManageMenu);

        btnFinish = findViewById(R.id.buttonFinishManage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageMenu.this,RecyclerView.VERTICAL,false);
        rvManageMenu.setLayoutManager(linearLayoutManager);
        rvManageMenu.setAdapter(rvAdapterMenu);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Dish> dishes = rvAdapterMenu.getDishOnRV();
                ArrayList<Dish> dishesOnDb = dbHandler.ReadDishes();

                dbHandler.UpdateDishes(dishes);


                Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

            }
        });

    }
}