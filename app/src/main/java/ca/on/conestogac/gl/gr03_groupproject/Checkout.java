package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Checkout extends AppCompatActivity {
    Bundle extras;
    TextView priceText;
    ListView list;
    Button btnFinish,btnReturn;
    DBHandler dbHandler;
    User user;
    ArrayList<Dish> dishOnCheckOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        extras = getIntent().getExtras();
        list = findViewById(R.id.listViewCheckout);
        priceText =findViewById(R.id.textViewPrice);
        btnFinish = findViewById(R.id.buttonFinishOrder);
        btnReturn = findViewById(R.id.buttonReturn);
        dbHandler = new DBHandler(Checkout.this);
        user = (User)extras.getSerializable("user");

        ArrayList<String> listText = new ArrayList<>();
        double finalPrice = 0.00;

        //Getting the list from the cart passed as extras
        dishOnCheckOut = (ArrayList<Dish>) extras.getSerializable("cart");

        //Add dish name and price to a listview
        //Add dish price to final price
        for (Dish d: dishOnCheckOut)
        {
            listText.add(d.Name + " - $" + d.Price) ;
            finalPrice += d.Price;
        }
        //Setting up the listview adapter
        ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,listText);
        list.setAdapter(arr);

        //Setting the text of final price to be the final price + 13% limited to 2 decimal digits
        priceText.setText(String.format("$%.2f",finalPrice*1.13));

        //OnClick method for the item inside the list
        //This method removes the selected item from the order and refreshes the page.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dish dish = dishOnCheckOut.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                builder.setMessage("Are you sure you want to remove " + dish.getName()).setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dishOnCheckOut.remove(dish);
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Checkout.class);
                        intent.putExtra("cart",dishOnCheckOut);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                }).setNegativeButton("No",null).show();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Checkout.this, MainMenu.class);
                intent.putExtra("cart", dishOnCheckOut);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        //Finished the order and adds it to the DB
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Checkout.this);
                builder.setMessage("Are you sure you want to finish ordering?").setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Order order = new Order(dishOnCheckOut,user);
                        if(validateOrder(order)){
                            if(dbHandler.AddOrder(order)){
                                Intent viewOrder = new Intent(getApplicationContext(), ViewOrder.class);
                                viewOrder.putExtra("order",order);
                                viewOrder.putExtra("user",user);
                                startActivity(viewOrder);
                            }
                            else{
                                Toast.makeText(Checkout.this, "Problem with order",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{
                            Toast.makeText(Checkout.this, "Cannot finish an empty order",Toast.LENGTH_SHORT).show();
                        }

                    }
                }).show();
            }
        });

    }

    //Validates if the order is not empty
    public boolean validateOrder(Order order){
        if(order.getOrderSize()>0){
            return true;
        }
        return false;
    }


}