package com.example.projectmcclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class adminLogin extends AppCompatActivity {
EditText user,pass;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
    }
    public void onLogin(View v)
    {
        if(user.getText().toString().equals("Admin")  && pass.getText().toString().equals("Pass"))
        {
            Intent intent = new Intent(adminLogin.this,admin.class);
            startActivity(intent);
        }
        else if(user.getText().toString() == null || user.getText().toString().isEmpty()) {
            Toast.makeText(v.getContext(),"Enter Username",Toast.LENGTH_SHORT).show();

        }
        else if(pass.getText().toString() == null || pass.getText().toString().isEmpty()) {
            Toast.makeText(v.getContext(),"Enter Password",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(v.getContext(),"Wrong Details",Toast.LENGTH_SHORT).show();
        }
    }
}
