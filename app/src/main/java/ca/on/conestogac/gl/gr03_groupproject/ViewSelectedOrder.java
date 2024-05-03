package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewSelectedOrder extends AppCompatActivity {

    Bundle extras;
    DBHandler dbHandler;

    ArrayList<Dish> dishesOnOrder;
    ArrayList<String> dishesText;

    Order order;

    TextView userTxt,orderTxt,dateTxt;
    Button btnReturn;

    ListView dishesList;

    User user;
    double orderCost = 0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_order);

        extras = getIntent().getExtras();

        dbHandler = new DBHandler(ViewSelectedOrder.this);

        int orderId = Integer.parseInt(extras.getString("orderId"));

        //order retrieved from dv
        order = dbHandler.getOrderById(orderId);
        //user linked to the order
        user = order.getUser();

        //Dishes linked to the order
        dishesOnOrder = order.getDishes();
        //Text that will be passed to the listview
        dishesText = new ArrayList<>();

        userTxt = findViewById(R.id.textViewUser);
        orderTxt = findViewById(R.id.textViewOrderNo);
        dateTxt = findViewById(R.id.textViewDate);
        btnReturn = findViewById(R.id.buttonBack);

        userTxt.setText(order.getUser().getName());
        orderTxt.setText("Order: " + String.valueOf(orderId));
        dateTxt.setText(order.getDate());

        dishesList = findViewById(R.id.listViewDishesOnOrder);

        //For each dish add the dish name and price to the listview
        //Add the price to be calculated for final price
        for (Dish d: dishesOnOrder) {
            dishesText.add(d.getName() + "  |   $" +d.getPrice());
            orderCost += d.getPrice();
        }
        dishesText.add("Order Total: $" + String.format("%.2f",orderCost*1.13));

        ArrayAdapter<String> arr = new ArrayAdapter<>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dishesText);
        dishesList.setAdapter(arr);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}