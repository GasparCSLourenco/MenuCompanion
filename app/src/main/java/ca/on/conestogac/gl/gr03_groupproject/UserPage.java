package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserPage extends AppCompatActivity {

    Button btnQuit;
    User user;
    Bundle extras;

    ImageView imageViewMenu,imageHistory;

    TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        extras = getIntent().getExtras();

        user = (User) extras.getSerializable("user");

        btnQuit = findViewById(R.id.buttonQuit);

        imageViewMenu = findViewById(R.id.imageViewOrder);
        imageHistory = findViewById(R.id.imageViewHistory);

        welcome = findViewById(R.id.textViewWelcome);

        String name[] = user.getName().split(" ");

        welcome.setText(welcome.getText().toString() + name[0]);


        imageHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent history = new Intent(getApplicationContext(), History.class);
                history.putExtra("user",user);
                startActivity(history);
            }
        });

        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(), ChooseTable.class);
                menu.putExtra("user",user);
                startActivity(menu);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent form = new Intent(getApplicationContext(), UserForm.class);
                startActivity(form);
            }
        });

    }
}