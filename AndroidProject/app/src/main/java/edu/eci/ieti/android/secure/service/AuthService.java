package edu.eci.ieti.android.secure.service;

import edu.eci.ieti.android.secure.model.LoginWrapper;
import edu.eci.ieti.android.secure.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth")
    Call<Token> login(@Body LoginWrapper loginWrapper);
}
