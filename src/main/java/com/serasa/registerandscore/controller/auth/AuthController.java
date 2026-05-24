package com.serasa.registerandscore.controller.auth;

import com.serasa.api.AuthApi;
import com.serasa.model.LoginRequest;
import com.serasa.model.LoginResponse;
import com.serasa.model.RegisterRequest;
import com.serasa.registerandscore.application.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        var token = authService.buildToken(loginRequest);
        var response = LoginResponse.builder().token(token).build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> register(RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(CREATED).build();
    }

}
