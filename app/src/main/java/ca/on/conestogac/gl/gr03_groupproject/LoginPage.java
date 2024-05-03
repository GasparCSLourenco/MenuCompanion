package ca.on.conestogac.gl.gr03_groupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

EditText textEmail, textPassword;

Button btnLogin;

DBHandler dbHandler;
User user;

private final String admin_email = "Admin";
private final String admin_password = "Admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        textEmail = findViewById(R.id.editTextEmailLogin);
        textPassword = findViewById(R.id.editTextPasswordLogin);
        btnLogin = findViewById(R.id.buttonLoginLogin);
        dbHandler = new DBHandler(LoginPage.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();

                if(email.equals(admin_email) && password.equals(admin_password)){
                    Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                    startActivity(intent);
                    return;
                }

                if(textEmail.getText().toString()!= "" && textPassword.getText().toString() != ""){
                    //Retrieving the user information from the database
                    user = dbHandler.getUserFromDb(email,password);
                }
                    //If the information provided do not match what is on the db, the user will be returned as null
                    //The information will have to be reentered
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(),UserPage.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginPage.this,"User not found, please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}