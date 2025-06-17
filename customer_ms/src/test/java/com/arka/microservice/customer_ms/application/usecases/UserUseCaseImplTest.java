package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.exception.BadRequestException;
import com.arka.microservice.customer_ms.domain.exception.DuplicateResourceException;
import com.arka.microservice.customer_ms.domain.exception.NotFoundException;
import com.arka.microservice.customer_ms.domain.exception.UnauthorizedException;
import com.arka.microservice.customer_ms.domain.model.RolModel;
import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IRolOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseImplTest {

    @Mock
    private IUserOutPort userOutPort;

    @Mock
    private IRolOutPort rolOutPort;

    @Mock
    private IPasswordEncodeOutPort passwordEncodeOutPort;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    private UserModel userModel;
    private RolModel userRolModel;
    private final String encodedPassword = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";

    @BeforeEach
    void setUp() {
        // Configurar rol de usuario
        userRolModel = new RolModel();
        userRolModel.setId(1L);
        userRolModel.setName(USER_ROLE_NAME);
        userRolModel.setDescription("Regular user role");
        userRolModel.setCreatedAt(LocalDateTime.now());
        userRolModel.setUpdatedAt(LocalDateTime.now());

        // Configurar usuario de prueba
        userModel = UserModel.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password("password123")
                .build();
    }

    @Test
    void registerUser_Success() {
        // Configurar comportamiento de los mocks
        when(rolOutPort.findByName(USER_ROLE_NAME)).thenReturn(Mono.just(userRolModel));
        when(userOutPort.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userOutPort.findByDni(anyString())).thenReturn(Mono.empty());
        when(passwordEncodeOutPort.encodePassword(anyString())).thenReturn(Mono.just(encodedPassword));
        
        UserModel savedUser = UserModel.builder()
                .id(1L)
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .email(userModel.getEmail())
                .phone(userModel.getPhone())
                .dni(userModel.getDni())
                .password(encodedPassword)
                .isActive(true)
                .roleId(userRolModel.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        when(userOutPort.save(any(UserModel.class))).thenReturn(Mono.just(savedUser));

        // Ejecutar el método a probar y verificar el resultado
        StepVerifier.create(userUseCase.registerUser(userModel))
                .expectNextMatches(result -> 
                    result.getId().equals(1L) &&
                    result.getFirstName().equals(userModel.getFirstName()) &&
                    result.getLastName().equals(userModel.getLastName()) &&
                    result.getEmail().equals(userModel.getEmail()) &&
                    result.getPhone().equals(userModel.getPhone()) &&
                    result.getDni().equals(userModel.getDni()) &&
                    result.getPassword().equals(encodedPassword) &&
                    result.getIsActive() &&
                    result.getRoleId().equals(userRolModel.getId()) &&
                    result.getCreatedAt() != null &&
                    result.getUpdatedAt() != null
                )
                .verifyComplete();
    }


    @Test
    void registerUser_DniAlreadyExists() {
        // Configurar comportamiento cuando el DNI ya existe
        when(rolOutPort.findByName(USER_ROLE_NAME)).thenReturn(Mono.just(userRolModel));
        when(userOutPort.findByEmail(userModel.getEmail())).thenReturn(Mono.empty());
        when(userOutPort.findByDni(userModel.getDni())).thenReturn(Mono.just(new UserModel()));

        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(userUseCase.registerUser(userModel))
                .expectErrorMatches(throwable -> 
                    throwable instanceof DuplicateResourceException && 
                    throwable.getMessage().equals(DNI_ALREADY_EXISTS))
                .verify();
    }
    
    @Test
    void getUserProfileInfo_Success() {
        // Configurar usuario completo para la respuesta
        UserModel completeUser = UserModel.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password(encodedPassword)
                .isActive(true)
                .roleId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail("test@example.com")).thenReturn(Mono.just(completeUser));
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.getUserProfileInfo()
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "test@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "test@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNextMatches(result -> 
                    result.getId().equals(1L) &&
                    result.getFirstName().equals("Test") &&
                    result.getLastName().equals("User") &&
                    result.getEmail().equals("test@example.com") &&
                    result.getPhone().equals("+123456789") &&
                    result.getDni().equals("12345678") &&
                    result.getPassword().equals(encodedPassword) &&
                    result.getIsActive() &&
                    result.getRoleId().equals(1L) &&
                    result.getCreatedAt() != null &&
                    result.getUpdatedAt() != null
                )
                .verifyComplete();
    }
    
    @Test
    void getUserProfileInfo_UserNotFound() {
        // Configurar comportamiento cuando el usuario no existe
        when(userOutPort.findByEmail("nonexistent@example.com")).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.getUserProfileInfo()
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "nonexistent@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "nonexistent@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals(USER_NOT_FOUND))
                .verify();
    }
    
    @Test
    void updateUserProfile_Success() {
        // Configurar usuario existente
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        UserModel existingUser = UserModel.builder()
                .id(1L)
                .firstName("Old Name")
                .lastName("Old Last")
                .email("test@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password(encodedPassword)
                .isActive(true)
                .roleId(1L)
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .build();
        
        // Configurar datos de actualización
        UserModel updateData = UserModel.builder()
                .firstName("New Name")
                .lastName("New Last")
                .phone("+987654321")
                .build();
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(anyString())).thenReturn(Mono.just(existingUser));
        when(userOutPort.save(any(UserModel.class))).thenAnswer(invocation -> {
            UserModel savedUser = invocation.getArgument(0);
            return Mono.just(savedUser);
        });
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.updateUserProfile(updateData)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "test@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "test@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNextMatches(result -> {
                    // Verificar que los campos se actualizaron correctamente
                    boolean basicFieldsMatch = 
                        result.getId().equals(1L) &&
                        result.getFirstName().equals("New Name") &&
                        result.getLastName().equals("New Last") &&
                        result.getEmail().equals("test@example.com") &&
                        result.getPhone().equals("+987654321") &&
                        result.getDni().equals("12345678") &&
                        result.getPassword().equals(encodedPassword) &&
                        result.getIsActive() &&
                        result.getRoleId().equals(1L);
                    
                    // Verificar que la fecha de creación no cambió
                    boolean createdAtUnchanged = result.getCreatedAt().equals(existingUser.getCreatedAt());
                    
                    // Verificar que la fecha de actualización se actualizó
                    boolean updatedAtChanged = result.getUpdatedAt() != null && 
                                              !result.getUpdatedAt().equals(existingUser.getUpdatedAt());
                    
                    return basicFieldsMatch && createdAtUnchanged && updatedAtChanged;
                })
                .verifyComplete();
    }
    
    @Test
    void updateUserProfile_UserNotFound() {
        // Configurar datos de actualización
        UserModel updateData = UserModel.builder()
                .firstName("New Name")
                .lastName("New Last")
                .phone("+987654321")
                .build();
        
        // Configurar comportamiento cuando el usuario no existe
        when(userOutPort.findByEmail(anyString())).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.updateUserProfile(updateData)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "nonexistent@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "nonexistent@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals(USER_NOT_FOUND))
                .verify();
    }
    
    @Test
    void updateUserProfile_InvalidFirstName() {
        // Configurar usuario existente
        UserModel existingUser = UserModel.builder()
                .id(1L)
                .firstName("Old Name")
                .lastName("Old Last")
                .email("test@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password(encodedPassword)
                .isActive(true)
                .roleId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Configurar datos de actualización con nombre inválido (vacío)
        UserModel updateData = UserModel.builder()
                .firstName("")
                .lastName("New Last")
                .phone("+987654321")
                .build();
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(anyString())).thenReturn(Mono.just(existingUser));
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.updateUserProfile(updateData)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "test@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "test@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BadRequestException && 
                    throwable.getMessage().equals(FIRST_NAME_REQUIRED))
                .verify();
    }
    
    @Test
    void updateUserProfile_InvalidPhone() {
        // Configurar usuario existente
        UserModel existingUser = UserModel.builder()
                .id(1L)
                .firstName("Old Name")
                .lastName("Old Last")
                .email("test@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password(encodedPassword)
                .isActive(true)
                .roleId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Configurar datos de actualización con teléfono inválido
        UserModel updateData = UserModel.builder()
                .firstName("New Name")
                .lastName("New Last")
                .phone("invalid-phone")
                .build();
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(anyString())).thenReturn(Mono.just(existingUser));
        
        // Ejecutar el método a probar con contexto de seguridad simulado
        StepVerifier.create(userUseCase.updateUserProfile(updateData)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return "test@example.com";
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return "test@example.com"; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectErrorMatches(throwable -> 
                    throwable instanceof BadRequestException && 
                    throwable.getMessage().equals(PHONE_FORMAT_ERROR))
                .verify();
    }
    
    @Test
    void listAllUsers_NoFilters() {
        // Crear lista de usuarios para la prueba
        UserModel user1 = UserModel.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user2 = UserModel.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .dni("87654321")
                .isActive(true)
                .roleId(1L)
                .build();
        
        // Configurar comportamiento del mock
        when(userOutPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(user1, user2));
        
        // Ejecutar el método a probar sin filtros y página 0
        StepVerifier.create(userUseCase.listAllUsers(Optional.empty(), Optional.empty(), Optional.empty(), 0))
                .expectNext(user1)
                .expectNext(user2)
                .verifyComplete();
    }
    
    @Test
    void listAllUsers_FilterByEmail() {
        // Crear lista de usuarios para la prueba
        UserModel user1 = UserModel.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user2 = UserModel.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .dni("87654321")
                .isActive(true)
                .roleId(1L)
                .build();
        
        // Configurar comportamiento del mock
        when(userOutPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(user1, user2));
        
        // Ejecutar el método a probar con filtro de email
        StepVerifier.create(userUseCase.listAllUsers(Optional.of("john@example.com"), Optional.empty(), Optional.empty(), 0))
                .expectNext(user1)
                .verifyComplete();
    }
    
    @Test
    void listAllUsers_FilterByDni() {
        // Crear lista de usuarios para la prueba
        UserModel user1 = UserModel.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user2 = UserModel.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .dni("87654321")
                .isActive(true)
                .roleId(1L)
                .build();
        
        // Configurar comportamiento del mock
        when(userOutPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(user1, user2));
        
        // Ejecutar el método a probar con filtro de DNI
        StepVerifier.create(userUseCase.listAllUsers(Optional.empty(), Optional.of("87654321"), Optional.empty(), 0))
                .expectNext(user2)
                .verifyComplete();
    }
    
    @Test
    void listAllUsers_FilterByName() {
        // Crear lista de usuarios para la prueba
        UserModel user1 = UserModel.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user2 = UserModel.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .dni("87654321")
                .isActive(true)
                .roleId(1L)
                .build();
        
        // Configurar comportamiento del mock
        when(userOutPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(user1, user2));
        
        // Ejecutar el método a probar con filtro de nombre
        StepVerifier.create(userUseCase.listAllUsers(Optional.empty(), Optional.empty(), Optional.of("Jane"), 0))
                .expectNext(user2)
                .verifyComplete();
    }
    
    @Test
    void listAllUsers_MultipleFilters() {
        // Crear lista de usuarios para la prueba
        UserModel user1 = UserModel.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user2 = UserModel.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("+987654321")
                .dni("87654321")
                .isActive(true)
                .roleId(1L)
                .build();
                
        UserModel user3 = UserModel.builder()
                .id(3L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .phone("+111222333")
                .dni("11223344")
                .isActive(true)
                .roleId(1L)
                .build();
        
        // Configurar comportamiento del mock
        when(userOutPort.findAll()).thenReturn(reactor.core.publisher.Flux.just(user1, user2, user3));
        
        // Ejecutar el método a probar con múltiples filtros
        StepVerifier.create(userUseCase.listAllUsers(Optional.empty(), Optional.empty(), Optional.of("Jane"), 0))
                .expectNext(user2)
                .expectNext(user3)
                .verifyComplete();
    }
    
    @Test
    void getAdminLogistic_Success() {
        // Crear usuario administrador logístico
        UserModel adminLogistic = UserModel.builder()
                .id(1L)
                .firstName("Admin")
                .lastName("Logistic")
                .email("admin.logistic@example.com")
                .phone("+123456789")
                .dni("12345678")
                .password(encodedPassword)
                .isActive(true)
                .roleId(3L) // ID del rol de administrador logístico
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Crear rol de administrador logístico
        RolModel adminLogisticRole = new RolModel();
        adminLogisticRole.setId(3L);
        adminLogisticRole.setName(ADMIN_LOGISTIC_ROLE_NAME);
        adminLogisticRole.setDescription("Admin Logistic Role");
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findById(1L)).thenReturn(Mono.just(adminLogistic));
        when(rolOutPort.findById(3L)).thenReturn(Mono.just(adminLogisticRole));
        
        // Ejecutar el método a probar
        StepVerifier.create(userUseCase.getAdminLogistic(1L))
                .expectNextMatches(result -> 
                    result.getId().equals(1L) &&
                    result.getFirstName().equals("Admin") &&
                    result.getLastName().equals("Logistic") &&
                    result.getEmail().equals("admin.logistic@example.com") &&
                    result.getRoleId().equals(3L)
                )
                .verifyComplete();
    }
    
    @Test
    void getAdminLogistic_UserNotFound() {
        // Configurar comportamiento cuando el usuario no existe
        when(userOutPort.findById(999L)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar
        StepVerifier.create(userUseCase.getAdminLogistic(999L))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals(USER_NOT_FOUND))
                .verify();
    }
    
    @Test
    void getAdminLogistic_RoleNotFound() {
        // Crear usuario con rol inexistente
        UserModel user = UserModel.builder()
                .id(1L)
                .firstName("User")
                .lastName("Test")
                .email("user@example.com")
                .roleId(999L) // ID de rol que no existe
                .build();
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findById(1L)).thenReturn(Mono.just(user));
        when(rolOutPort.findById(999L)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar
        StepVerifier.create(userUseCase.getAdminLogistic(1L))
                .expectErrorMatches(throwable -> 
                    throwable instanceof NotFoundException && 
                    throwable.getMessage().equals(USER_ROLE_NOT_FOUND))
                .verify();
    }
    
    @Test
    void getAdminLogistic_NotAdminLogistic() {
        // Crear usuario con rol diferente a administrador logístico
        UserModel user = UserModel.builder()
                .id(1L)
                .firstName("Regular")
                .lastName("User")
                .email("regular@example.com")
                .roleId(1L) // ID de rol regular
                .build();
        
        // Crear rol regular (no administrador logístico)
        RolModel regularRole = new RolModel();
        regularRole.setId(1L);
        regularRole.setName("ROLE_USER");
        regularRole.setDescription("Regular User Role");
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findById(1L)).thenReturn(Mono.just(user));
        when(rolOutPort.findById(1L)).thenReturn(Mono.just(regularRole));
        
        // Ejecutar el método a probar
        StepVerifier.create(userUseCase.getAdminLogistic(1L))
                .expectErrorMatches(throwable -> 
                    throwable instanceof UnauthorizedException && 
                    throwable.getMessage().equals(NOT_ADMIN_LOGISTIC))
                .verify();
    }
}