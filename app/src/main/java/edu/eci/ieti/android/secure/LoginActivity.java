package edu.eci.ieti.android.secure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickButton(View view){
        EditText editTextEmail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        System.out.println(email+ " " + password);
    }
}