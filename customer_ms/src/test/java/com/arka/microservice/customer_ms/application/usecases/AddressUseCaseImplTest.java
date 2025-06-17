package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.exception.NotFoundException;
import com.arka.microservice.customer_ms.domain.model.AddressModel;
import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.IAddressOutPort;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressUseCaseImplTest {

    @Mock
    private IAddressOutPort addressOutPort;

    @Mock
    private IUserOutPort userOutPort;

    @InjectMocks
    private AddressUseCaseImpl addressUseCase;

    private UserModel userModel;
    private AddressModel addressModel;
    private AddressModel existingAddressModel;
    private AddressModel updatedAddressModel;
    private final String userEmail = "test@example.com";
    private final Long addressId = 1L;

    @BeforeEach
    void setUp() {
        // Configurar usuario de prueba
        userModel = UserModel.builder()
                .id(1L)
                .firstName("Test")
                .lastName("User")
                .email(userEmail)
                .phone("+123456789")
                .dni("12345678")
                .isActive(true)
                .build();

        // Configurar dirección de prueba
        addressModel = new AddressModel();
        addressModel.setAddress("123 Test Street");
        addressModel.setCity("Test City");
        addressModel.setState("Test State");
        addressModel.setCountry("Test Country");
        addressModel.setZipCode("12345");
        
        // Configurar dirección existente para actualizar
        existingAddressModel = new AddressModel();
        existingAddressModel.setId(addressId);
        existingAddressModel.setAddress("123 Old Street");
        existingAddressModel.setCity("Old City");
        existingAddressModel.setState("Old State");
        existingAddressModel.setCountry("Old Country");
        existingAddressModel.setZipCode("54321");
        existingAddressModel.setUser(userModel);
        existingAddressModel.setCreatedAt(LocalDateTime.now().minusDays(1));
        existingAddressModel.setUpdatedAt(LocalDateTime.now().minusDays(1));
        
        // Configurar dirección actualizada
        updatedAddressModel = new AddressModel();
        updatedAddressModel.setAddress("123 New Street");
        updatedAddressModel.setCity("New City");
        updatedAddressModel.setState("New State");
        updatedAddressModel.setCountry("New Country");
        updatedAddressModel.setZipCode("12345");
    }

    @Test
    void addAddress_Success() {
        // Configurar el comportamiento de los mocks
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        
        // Configurar el comportamiento del addressOutPort para guardar la dirección
        AddressModel savedAddress = new AddressModel();
        savedAddress.setId(1L);
        savedAddress.setAddress(addressModel.getAddress());
        savedAddress.setCity(addressModel.getCity());
        savedAddress.setState(addressModel.getState());
        savedAddress.setCountry(addressModel.getCountry());
        savedAddress.setZipCode(addressModel.getZipCode());
        savedAddress.setUser(userModel);
        savedAddress.setCreatedAt(LocalDateTime.now());
        savedAddress.setUpdatedAt(LocalDateTime.now());
        
        when(addressOutPort.save(any(AddressModel.class))).thenReturn(Mono.just(savedAddress));
        
        // Usar un método de prueba que permita inyectar el email autenticado
        StepVerifier.create(addressUseCase.addAddress(addressModel)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNextMatches(result -> 
                    result.getId().equals(1L) &&
                    result.getAddress().equals(addressModel.getAddress()) &&
                    result.getCity().equals(addressModel.getCity()) &&
                    result.getState().equals(addressModel.getState()) &&
                    result.getCountry().equals(addressModel.getCountry()) &&
                    result.getZipCode().equals(addressModel.getZipCode()) &&
                    result.getUser() != null &&
                    result.getUser().getId().equals(userModel.getId()) &&
                    result.getCreatedAt() != null &&
                    result.getUpdatedAt() != null
                )
                .verifyComplete();
    }

    @Test
    void addAddress_UserNotFound() {
        // Configurar comportamiento cuando el usuario no se encuentra
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(addressUseCase.addAddress(addressModel)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectError(NotFoundException.class)
                .verify();
    }
    
    @Test
    void updateAddress_Success() {
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findByIdAndUserId(addressId, userModel.getId())).thenReturn(Mono.just(existingAddressModel));
        
        // Configurar el comportamiento para guardar la dirección actualizada
        AddressModel savedUpdatedAddress = new AddressModel();
        savedUpdatedAddress.setId(addressId);
        savedUpdatedAddress.setAddress(updatedAddressModel.getAddress());
        savedUpdatedAddress.setCity(updatedAddressModel.getCity());
        savedUpdatedAddress.setState(updatedAddressModel.getState());
        savedUpdatedAddress.setCountry(updatedAddressModel.getCountry());
        savedUpdatedAddress.setZipCode(updatedAddressModel.getZipCode());
        savedUpdatedAddress.setUser(userModel);
        savedUpdatedAddress.setCreatedAt(existingAddressModel.getCreatedAt());
        savedUpdatedAddress.setUpdatedAt(LocalDateTime.now());
        
        when(addressOutPort.save(any(AddressModel.class))).thenReturn(Mono.just(savedUpdatedAddress));
        
        // Ejecutar el método a probar y verificar el resultado
        StepVerifier.create(addressUseCase.updateAddress(addressId, updatedAddressModel)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNextMatches(result -> 
                    result.getId().equals(addressId) &&
                    result.getAddress().equals(updatedAddressModel.getAddress()) &&
                    result.getCity().equals(updatedAddressModel.getCity()) &&
                    result.getState().equals(updatedAddressModel.getState()) &&
                    result.getCountry().equals(updatedAddressModel.getCountry()) &&
                    result.getZipCode().equals(updatedAddressModel.getZipCode()) &&
                    result.getUser() != null &&
                    result.getUser().getId().equals(userModel.getId()) &&
                    result.getCreatedAt() != null &&
                    result.getUpdatedAt() != null
                )
                .verifyComplete();
    }
    
    @Test
    void updateAddress_AddressNotFound() {
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findByIdAndUserId(addressId, userModel.getId())).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(addressUseCase.updateAddress(addressId, updatedAddressModel)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectError(NotFoundException.class)
                .verify();
    }
    
    @Test
    void listUserAddresses_Success() {
        // Crear lista de direcciones para el usuario
        AddressModel address1 = new AddressModel();
        address1.setId(1L);
        address1.setAddress("123 Main St");
        address1.setCity("Main City");
        address1.setState("Main State");
        address1.setCountry("Main Country");
        address1.setZipCode("12345");
        address1.setUser(userModel);
        address1.setCreatedAt(LocalDateTime.now());
        address1.setUpdatedAt(LocalDateTime.now());
        
        AddressModel address2 = new AddressModel();
        address2.setId(2L);
        address2.setAddress("456 Second St");
        address2.setCity("Second City");
        address2.setState("Second State");
        address2.setCountry("Second Country");
        address2.setZipCode("67890");
        address2.setUser(userModel);
        address2.setCreatedAt(LocalDateTime.now());
        address2.setUpdatedAt(LocalDateTime.now());
        
        List<AddressModel> addresses = Arrays.asList(address1, address2);
        
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findAllByUserId(userModel.getId())).thenReturn(Flux.fromIterable(addresses));
        
        // Ejecutar el método a probar y verificar el resultado
        StepVerifier.create(addressUseCase.listUserAddresses()
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNext(address1, address2)
                .verifyComplete();
    }
    
    @Test
    void listUserAddresses_UserNotFound() {
        // Configurar comportamiento cuando el usuario no se encuentra
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(addressUseCase.listUserAddresses()
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectError(NotFoundException.class)
                .verify();
    }
    
    @Test
    void listUserAddresses_EmptyList() {
        // Configurar comportamiento cuando el usuario existe pero no tiene direcciones
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findAllByUserId(userModel.getId())).thenReturn(Flux.empty());
        
        // Ejecutar el método a probar y verificar que se devuelve un Flux vacío
        StepVerifier.create(addressUseCase.listUserAddresses()
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .verifyComplete();
    }
    
    @Test
    void deleteAddress_Success() {
        // Configurar comportamiento de los mocks
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findByIdAndUserId(addressId, userModel.getId())).thenReturn(Mono.just(existingAddressModel));
        when(addressOutPort.delete(existingAddressModel)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar el resultado
        StepVerifier.create(addressUseCase.deleteAddress(addressId)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectNext("Address deleted successfully")
                .verifyComplete();
    }
    
    @Test
    void deleteAddress_UserNotFound() {
        // Configurar comportamiento cuando el usuario no se encuentra
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(addressUseCase.deleteAddress(addressId)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectError(NotFoundException.class)
                .verify();
    }
    
    @Test
    void deleteAddress_AddressNotFound() {
        // Configurar comportamiento cuando la dirección no se encuentra
        when(userOutPort.findByEmail(userEmail)).thenReturn(Mono.just(userModel));
        when(addressOutPort.findByIdAndUserId(addressId, userModel.getId())).thenReturn(Mono.empty());
        
        // Ejecutar el método a probar y verificar que se lanza la excepción esperada
        StepVerifier.create(addressUseCase.deleteAddress(addressId)
                .contextWrite(context -> ReactiveSecurityContextHolder.withAuthentication(
                        new Authentication() {
                            @Override
                            public String getName() {
                                return userEmail;
                            }
                            
                            // Implementaciones mínimas requeridas
                            @Override public java.util.Collection getAuthorities() { return java.util.Collections.emptyList(); }
                            @Override public Object getCredentials() { return null; }
                            @Override public Object getDetails() { return null; }
                            @Override public Object getPrincipal() { return userEmail; }
                            @Override public boolean isAuthenticated() { return true; }
                            @Override public void setAuthenticated(boolean b) {}
                        }
                )))
                .expectError(NotFoundException.class)
                .verify();
    }
}