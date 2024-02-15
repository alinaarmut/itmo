package aca98b.web4l.service;

import aca98b.web4l.annotation.LogBadRequest;
import aca98b.web4l.model.entities.Role;
import aca98b.web4l.model.entities.Token;
import aca98b.web4l.model.entities.TokenType;
import aca98b.web4l.model.entities.User;
import aca98b.web4l.model.entities.repo.TokenRepository;
import aca98b.web4l.model.entities.repo.UserRepository;
import aca98b.web4l.model.request.AuthRequest;
import aca98b.web4l.model.response.AuthResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUsername());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach( t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @LogBadRequest
    @Transactional
    public AuthResponse register(AuthRequest request) {
        log.info("registering user...");
        if (repository.existsByUsername(request.getUsername())) {
            log.info("user already exists");
            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Username taken.")
                    .status(HttpStatus.CONFLICT)
                    .statusCode(HttpStatus.CONFLICT.value())
                    .username(request.getUsername())
                    .build();
        } else {
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .username(request.getUsername())
                    .build();
            var savedUser = repository.save(user);

            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("User registered.")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .jwt(jwtToken)
                    .username(request.getUsername())
                    .build();
        }
    }

    @LogBadRequest
    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        log.info("searching for user {}", request.getUsername());
        if (repository.existsByUsername(request.getUsername())) {
            if (passwordEncoder.matches(request.getPassword(), repository.findByUsername(request.getUsername()).getPassword())) {
                log.info("logged in");

                User user = repository.findByUsername(request.getUsername());
                String jwtToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                return AuthResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("User logged in.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .jwt(jwtToken)
                        .username(request.getUsername())
                        .build();

            }
            log.info("incorrect password");
            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Incorrect password.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .username(request.getUsername())
                    .build();
        }
        log.info("user {}: not found", request.getUsername());
        return AuthResponse.builder()
                .timeStamp(LocalDateTime.now())
                .message("Incorrect username.")
                .status(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .username(request.getUsername())
                .build();
    }
}