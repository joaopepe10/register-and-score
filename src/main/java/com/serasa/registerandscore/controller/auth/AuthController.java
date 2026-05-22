package com.serasa.registerandscore.controller.auth;

import com.serasa.api.AuthApi;
import com.serasa.model.LoginRequest;
import com.serasa.model.LoginResponse;
import com.serasa.registerandscore.core.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(getAuthentication(loginRequest));
        var token = tokenService.generateToken(authentication);
        var response = LoginResponse.builder().token(token).build();
        return ResponseEntity.ok(response);
    }

    @NonNull
    private static UsernamePasswordAuthenticationToken getAuthentication(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
