package edu.eci.ieti.android.secure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.android.secure.model.LoginWrapper;
import edu.eci.ieti.android.secure.model.Token;
import edu.eci.ieti.android.secure.service.AuthService;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );

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

        System.out.println(email + ": " + password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService authService = retrofit.create(AuthService.class);
        executorService.execute( new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    // Default credentials are "test@mail.com" and "password"
                    Response<Token> response =
                            authService.login( new LoginWrapper( email, password ) ).execute();

                    if (response.isSuccessful()) {
                        Token token = response.body();
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TOKEN_KEY", token.getAccessToken());
                        editor.commit();
                        startLaunch();
                    }
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        } );

    }

    public void startLaunch(){
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
    }
}