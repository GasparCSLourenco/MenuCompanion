package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    private ArrayList<Dish> dishList;
    public ArrayList<Dish> dishOnCart;
    private DBHandler dbHandler;
    private RVAdapterDish rvAdapter;
    private RecyclerView dishRv;
    User user;

    ImageButton cart;
    Button btnAdd;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        extras = getIntent().getExtras();

        user = (User)extras.getSerializable("user");
        dishOnCart = (ArrayList<Dish>)extras.getSerializable("cart");

        //Reference for checkout button for next page.
        cart = findViewById(R.id.imageButtonCart);

        //Db
        dbHandler = new DBHandler(MainMenu.this);

        //Adding the list of dishes in the DB to the list
        dishList = dbHandler.ReadDishes();
        ArrayList<Dish> availableDishes = new ArrayList<>();

        for (Dish d : dishList
             ) {
            if(d.getAvailable()){
                availableDishes.add(d);
            }
        }


        //Instantiating an adapter for the Recycler view
        rvAdapter = new RVAdapterDish(MainMenu.this,availableDishes,dishOnCart);
        //Reference for the recycler view
        dishRv = findViewById(R.id.menuRV);

        //Setting up and inflating the adapter
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(MainMenu.this, RecyclerView.VERTICAL,false);
        dishRv.setLayoutManager(linearLayoutManager);
        dishRv.setAdapter(rvAdapter);


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOut = new Intent(getApplicationContext(), Checkout.class);
                dishOnCart = rvAdapter.ReturnCart();
                checkOut.putExtra("cart",dishOnCart);
                checkOut.putExtra("user",user);
                startActivity(checkOut);
            }
        });
    }

    public void AddDishToCart(Dish dish){
        dishOnCart.add(dish);
    }

}