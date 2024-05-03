package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewOrder extends AppCompatActivity {

    TextView list, message;
    Bundle extras;

    Button btnReturn;

    Order order;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        message = findViewById(R.id.textViewMessage);
        btnReturn = findViewById(R.id.buttonReturnToMenu);

        extras = getIntent().getExtras();

        order = (Order) extras.getSerializable("order");
        user = (User) extras.getSerializable("user");

        String messageText = " your order is being prepared";

        String name = order.getUser().getName().toString();

        if(!name.equals("Guest")){
            message.setText(name + messageText);
        }

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getName().equals("Guest")){
                    Intent userForm = new Intent(getApplicationContext(), UserForm.class);
                    startActivity(userForm);
                }
                else{
                    Intent userPage = new Intent(getApplicationContext(), UserPage.class);
                    userPage.putExtra("user",user);
                    startActivity(userPage);
                }

            }
        });



    }
}