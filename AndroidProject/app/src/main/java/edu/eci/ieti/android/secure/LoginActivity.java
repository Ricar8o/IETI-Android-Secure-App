package edu.eci.ieti.android.secure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.android.secure.model.LoginWrapper;
import edu.eci.ieti.android.secure.model.Token;
import edu.eci.ieti.android.secure.service.AuthService;
import retrofit2.Call;
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
                    Response<Token> response =
                            authService.login( new LoginWrapper( "test@mail.com", "password" ) ).execute();
                    Token token = response.body();
                    System.out.println(token);
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        } );
    }
}