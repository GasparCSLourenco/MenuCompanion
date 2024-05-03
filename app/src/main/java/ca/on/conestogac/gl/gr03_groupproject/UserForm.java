package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.nio.file.attribute.UserPrincipalNotFoundException;

public class UserForm extends AppCompatActivity {

    EditText firstName, lastName, password, email;

    Button create,login,guest;

    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        create = findViewById(R.id.buttonCreate);
        login = findViewById(R.id.buttonLogin);
        guest = findViewById(R.id.buttonGuest);

        firstName = findViewById(R.id.editTextName);
        lastName = findViewById(R.id.editTextLast);
        password= findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);

        create.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);
        guest.setOnClickListener(this::onClick);

        dbHandler = new DBHandler(UserForm.this);



    }

    public void onClick(View v) {
        int id = v.getId();


        //Collecting user information to pass to DB
        if(id == R.id.buttonCreate){
            String name = firstName.getText().toString();
            String lastN = lastName.getText().toString();
            String passwordNo = password.getText().toString();
            String emailAddress = email.getText().toString();

            String fullName = name + " " + lastN;

            if(ValidateForm(fullName,emailAddress,passwordNo)){
                User user = new User(fullName,emailAddress,passwordNo);

                Intent userPage = new Intent(getApplicationContext(), UserPage.class);
                //Adding the new user to the extras for next activity
                userPage.putExtra("user",user);
                dbHandler.AddUser(user);
                startActivity(userPage);

            }
        }
        //Show login page
        else if(id == R.id.buttonLogin){
            Intent login = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(login);
            return;
        }
        //Enter as guest
        else if(id == R.id.buttonGuest) {
            Intent intent = new Intent(getApplicationContext(), ChooseTable.class);
            intent.putExtra("user",new User());
            startActivity(intent);
        }

    }


    public boolean ValidateForm(String name, String email, String password){

        if(name.length()<2 || email.length()<2 || password.length()<2){
            return false;
        }

        for (Character c: name.toCharArray()) {
            if(Character.isDigit(c)){
                return false;
            }
        }

        return true;

    }
}