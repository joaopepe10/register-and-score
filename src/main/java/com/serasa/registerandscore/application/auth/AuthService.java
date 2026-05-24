package com.serasa.registerandscore.application.auth;

import com.serasa.model.LoginRequest;
import com.serasa.model.RegisterRequest;
import com.serasa.registerandscore.core.security.TokenService;
import com.serasa.registerandscore.infra.persistence.sql.user.UserRepository;
import com.serasa.registerandscore.infra.persistence.sql.user.model.UserEntity;
import com.serasa.registerandscore.infra.persistence.sql.person.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String buildToken(LoginRequest loginRequest) {
        var auth = getAuthentication(loginRequest);
        var authentication = authenticationManager.authenticate(auth);
        return tokenService.generateToken(authentication);
    }

    public void register(RegisterRequest registerRequest) {
        var passwordEncoded = passwordEncoder.encode(registerRequest.getPassword());
        var role = UserRole.valueOf(registerRequest.getRole().name());
        var user = UserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoded)
                .role(role)
                .build();
        userRepository.save(user);
    }

    @NonNull
    private static UsernamePasswordAuthenticationToken getAuthentication(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    }

}
