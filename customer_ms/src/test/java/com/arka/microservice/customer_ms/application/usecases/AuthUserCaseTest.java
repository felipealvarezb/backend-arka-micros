package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.ICartOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.infra.driver.rest.security.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.INVALID_PASSWORD;
import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.USER_EMAIL_NOT_FOUND;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserCaseTest {

    @Mock
    private IUserOutPort userOutPort;

    @Mock
    private IPasswordEncodeOutPort passwordEncodeOutPort;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ICartOutPort cartOutPort;

    @InjectMocks
    private AuthUserCase authUserCase;

    private UserModel userModel;
    private final String email = "test@example.com";
    private final String password = "password123";
    private final String encodedPassword = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";
    private final String token = "jwt.token.here";

    @BeforeEach
    void setUp() {
        userModel = UserModel.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email(email)
                .password(encodedPassword)
                .isActive(true)
                .roleId(1L)
                .build();
    }

    @Test
    void authenticate_Success() {
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(email)).thenReturn(Mono.just(userModel));
        when(passwordEncodeOutPort.matchesPassword(password, encodedPassword)).thenReturn(Mono.just(true));
        when(jwtUtil.generateToken(userModel)).thenReturn(Mono.just(token));
        when(cartOutPort.createCart(anyLong(), anyString())).thenReturn(Mono.just("Cart created"));

        // Ejecutar el método a probar y verificar el resultado
        StepVerifier.create(authUserCase.authenticate(email, password))
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void authenticate_UserNotFound() {
        // Configurar comportamiento cuando el usuario no se encuentra
        when(userOutPort.findByEmail(email)).thenReturn(Mono.empty());

        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(authUserCase.authenticate(email, password))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BadCredentialsException && 
                    throwable.getMessage().equals(USER_EMAIL_NOT_FOUND))
                .verify();
    }

    @Test
    void authenticate_InvalidPassword() {
        // Configurar comportamiento cuando la contraseña es incorrecta
        when(userOutPort.findByEmail(email)).thenReturn(Mono.just(userModel));
        when(passwordEncodeOutPort.matchesPassword(password, encodedPassword)).thenReturn(Mono.just(false));

        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(authUserCase.authenticate(email, password))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BadCredentialsException && 
                    throwable.getMessage().equals(INVALID_PASSWORD))
                .verify();
    }
}