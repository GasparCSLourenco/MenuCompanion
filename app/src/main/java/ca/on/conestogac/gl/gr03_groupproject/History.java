package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class History extends AppCompatActivity {


    ListView listHistory;
    DBHandler dbHandler;
    Bundle extras;

    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listHistory = findViewById(R.id.listViewHistory);
        btnReturn = findViewById(R.id.buttonReturnToUser);

        dbHandler = new DBHandler(History.this);

        extras = getIntent().getExtras();

        User user = (User)extras.getSerializable("user");

        //List of orders linked to user
        ArrayList<String> orderList = new ArrayList<>();

        ArrayList<Order> orders = dbHandler.getOrdersByUser(user);
        //List of Order ids
        ArrayList<Integer> orderIds = dbHandler.getOrderIdsByUser(user);
        //Adding orders to the listview
        for (int id:orderIds) {
            orderList.add("Order: " + String.valueOf(id));
        }

        ArrayAdapter<String> arr = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,orderList);
        listHistory.setAdapter(arr);

        listHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewSelectedOrder = new Intent(getApplicationContext(), ViewSelectedOrder.class);
                String orderText = (String) listHistory.getItemAtPosition(position);
                String orderInfo[] = orderText.split(" ");
                viewSelectedOrder.putExtra("orderId",orderInfo[1]);
                startActivity(viewSelectedOrder);

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userPage = new Intent(getApplicationContext(), UserPage.class);
                userPage.putExtra("user",user);
                startActivity(userPage);
            }
        });

    }
}