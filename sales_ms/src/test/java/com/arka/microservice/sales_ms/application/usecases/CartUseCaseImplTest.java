package com.arka.microservice.sales_ms.application.usecases;

import com.arka.microservice.sales_ms.domain.exception.NotFoundException;
import com.arka.microservice.sales_ms.domain.model.CartModel;
import com.arka.microservice.sales_ms.domain.ports.out.ICartDetailOutPort;
import com.arka.microservice.sales_ms.domain.ports.out.ICartOutPort;
import com.arka.microservice.sales_ms.domain.ports.out.IInventoryOutPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.arka.microservice.sales_ms.domain.Util.CartConstant.CART_ACTIVE;
import static com.arka.microservice.sales_ms.domain.Util.CartConstant.CART_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartUseCaseImplTest {

    @Mock
    private ICartOutPort cartOutPort;

    @Mock
    private IInventoryOutPort inventoryOutPort;

    @Mock
    private ICartDetailOutPort cartDetailOutPort;

    @InjectMocks
    private CartUseCaseImpl cartUseCase;

    @Test
    void createCart_Success() {
        // Arrange
        Long userId = 1L;
        CartModel savedCart = new CartModel();
        savedCart.setId(1L);
        savedCart.setUserId(userId);
        savedCart.setStatus(CART_ACTIVE);
        savedCart.setCartDetails(new ArrayList<>());
        savedCart.setCreatedAt(LocalDateTime.now());
        savedCart.setUpdatedAt(LocalDateTime.now());

        when(cartOutPort.save(any(CartModel.class))).thenReturn(Mono.just(savedCart));

        // Act & Assert
        StepVerifier.create(cartUseCase.createCart(userId))
                .expectNext(CART_CREATED)
                .verifyComplete();

        // Verify cart properties
        ArgumentCaptor<CartModel> cartCaptor = ArgumentCaptor.forClass(CartModel.class);
        verify(cartOutPort).save(cartCaptor.capture());
        
        CartModel capturedCart = cartCaptor.getValue();
        assertEquals(userId, capturedCart.getUserId());
        assertEquals(CART_ACTIVE, capturedCart.getStatus());
        assertNotNull(capturedCart.getCartDetails());
        assertEquals(0, capturedCart.getCartDetails().size());
        assertNotNull(capturedCart.getCreatedAt());
        assertNotNull(capturedCart.getUpdatedAt());
    }

    @Test
    void createCart_ErrorInSave() {
        // Arrange
        Long userId = 1L;
        RuntimeException expectedException = new RuntimeException("Database error");
        
        when(cartOutPort.save(any(CartModel.class))).thenReturn(Mono.error(expectedException));

        // Act & Assert
        StepVerifier.create(cartUseCase.createCart(userId))
                .expectErrorMatches(throwable -> 
                    throwable instanceof RuntimeException && 
                    throwable.getMessage().equals("Database error"))
                .verify();
                
        verify(cartOutPort).save(any(CartModel.class));
    }
    
    @Test
    void insertProductToCart_Success() {
        // Arrange
        Long cartId = 1L;
        Long productId = 2L;
        Long inventoryId = 3L;
        int quantityToAdd = 1;
        double productPrice = 10.0;
        
        // Mock inventory with sufficient stock
        com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto inventoryDto = 
            new com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto();
        inventoryDto.setId(inventoryId);
        inventoryDto.setProductId(productId);
        inventoryDto.setActualStock(5); // Sufficient stock
        
        // Mock product details
        com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto productDto = 
            new com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto();
        productDto.setPrice(productPrice);
        
        // Mock saved cart detail
        com.arka.microservice.sales_ms.domain.model.CartDetailModel savedDetail = 
            new com.arka.microservice.sales_ms.domain.model.CartDetailModel();
        savedDetail.setId(1L);
        savedDetail.setCartId(cartId);
        savedDetail.setProductId(productId);
        savedDetail.setQuantity(quantityToAdd);
        savedDetail.setSubtotal(productPrice * quantityToAdd);
        
        // Configure mocks
        when(inventoryOutPort.getProductInventory(productId)).thenReturn(Mono.just(inventoryDto));
        when(inventoryOutPort.removeStockFromInventory(inventoryId, productId, quantityToAdd))
            .thenReturn(Mono.just("Stock removed"));
        when(inventoryOutPort.getProductById(productId)).thenReturn(Mono.just(productDto));
        when(cartDetailOutPort.save(any())).thenReturn(Mono.just(savedDetail));
        
        // Act & Assert
        StepVerifier.create(cartUseCase.insertProductToCart(cartId, productId))
            .expectNext("Product added to cart successfully")
            .verifyComplete();
        
        // Verify interactions
        verify(inventoryOutPort).getProductInventory(productId);
        verify(inventoryOutPort).removeStockFromInventory(inventoryId, productId, quantityToAdd);
        verify(inventoryOutPort).getProductById(productId);
        
        // Verify cart detail properties
        ArgumentCaptor<com.arka.microservice.sales_ms.domain.model.CartDetailModel> detailCaptor = 
            ArgumentCaptor.forClass(com.arka.microservice.sales_ms.domain.model.CartDetailModel.class);
        verify(cartDetailOutPort).save(detailCaptor.capture());
        
        com.arka.microservice.sales_ms.domain.model.CartDetailModel capturedDetail = detailCaptor.getValue();
        assertEquals(cartId, capturedDetail.getCartId());
        assertEquals(productId, capturedDetail.getProductId());
        assertEquals(quantityToAdd, capturedDetail.getQuantity());
        assertEquals(productPrice * quantityToAdd, capturedDetail.getSubtotal());
        assertNotNull(capturedDetail.getCreatedAt());
        assertNotNull(capturedDetail.getUpdatedAt());
    }
    
    @Test
    void insertProductToCart_InsufficientStock() {
        // Arrange
        Long cartId = 1L;
        Long productId = 2L;
        int quantityToAdd = 1;
        
        // Mock inventory with insufficient stock
        com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto inventoryDto = 
            new com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto();
        inventoryDto.setActualStock(0); // No stock available
        
        // Configure mock
        when(inventoryOutPort.getProductInventory(productId)).thenReturn(Mono.just(inventoryDto));
        
        // Act & Assert
        StepVerifier.create(cartUseCase.insertProductToCart(cartId, productId))
            .expectErrorMatches(throwable -> 
                throwable instanceof RuntimeException && 
                throwable.getMessage().equals("Sold Out"))
            .verify();
        
        // Verify interactions
        verify(inventoryOutPort).getProductInventory(productId);
        verify(inventoryOutPort, never()).removeStockFromInventory(any(), any(), anyInt());
        verify(inventoryOutPort, never()).getProductById(any());
        verify(cartDetailOutPort, never()).save(any());
    }
    
    @Test
    void insertProductToCart_InventoryNotFound() {
        // Arrange
        Long cartId = 1L;
        Long productId = 2L;
        Long inventoryId = 3L;
        double productPrice = 10.0;
        
        // Mock product details for the flow after empty inventory
        com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto productDto = 
            new com.arka.microservice.sales_ms.infra.driven.webclient.dto.ProductDto();
        productDto.setPrice(productPrice);
        
        // Mock saved cart detail
        com.arka.microservice.sales_ms.domain.model.CartDetailModel savedDetail = 
            new com.arka.microservice.sales_ms.domain.model.CartDetailModel();
        savedDetail.setId(1L);
        
        // Configure mocks
        when(inventoryOutPort.getProductInventory(productId)).thenReturn(Mono.error(new RuntimeException("Inventory not found")));
        
        // Act & Assert
        StepVerifier.create(cartUseCase.insertProductToCart(cartId, productId))
            .expectErrorMatches(throwable -> 
                throwable instanceof RuntimeException && 
                throwable.getMessage().equals("Inventory not found"))
            .verify();
        
        // Verify interactions
        verify(inventoryOutPort).getProductInventory(productId);
        verify(inventoryOutPort, never()).removeStockFromInventory(any(), any(), anyInt());
        verify(inventoryOutPort, never()).getProductById(any());
        verify(cartDetailOutPort, never()).save(any());
    }
    
    @Test
    void removeProductFromCart_Success() {
        // Arrange
        Long cartId = 1L;
        Long productId = 2L;
        Long cartDetailId = 3L;
        Long inventoryId = 4L;
        int quantityInCart = 2;
        
        // Mock cart detail found in cart
        com.arka.microservice.sales_ms.domain.model.CartDetailModel cartDetail = 
            new com.arka.microservice.sales_ms.domain.model.CartDetailModel();
        cartDetail.setId(cartDetailId);
        cartDetail.setCartId(cartId);
        cartDetail.setProductId(productId);
        cartDetail.setQuantity(quantityInCart);
        
        // Mock inventory
        com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto inventoryDto = 
            new com.arka.microservice.sales_ms.infra.driven.webclient.dto.InventoryDto();
        inventoryDto.setId(inventoryId);
        inventoryDto.setProductId(productId);
        
        // Configure mocks
        when(cartDetailOutPort.findByCartIdAndProductId(cartId, productId)).thenReturn(Mono.just(cartDetail));
        when(inventoryOutPort.getProductInventory(productId)).thenReturn(Mono.just(inventoryDto));
        when(inventoryOutPort.addStockToInventory(inventoryId, productId, quantityInCart))
            .thenReturn(Mono.just("Stock added"));
        when(cartDetailOutPort.delete(cartDetailId)).thenReturn(Mono.empty());
        
        // Act & Assert
        StepVerifier.create(cartUseCase.removeProductFromCart(cartId, productId))
            .expectNext("Product removed from cart successfully")
            .verifyComplete();
        
        // Verify interactions
        verify(cartDetailOutPort).findByCartIdAndProductId(cartId, productId);
        verify(inventoryOutPort).getProductInventory(productId);
        verify(inventoryOutPort).addStockToInventory(inventoryId, productId, quantityInCart);
        verify(cartDetailOutPort).delete(cartDetailId);
    }
    
    @Test
    void removeProductFromCart_ProductNotFoundInCart() {
        // Arrange
        Long cartId = 1L;
        Long productId = 2L;
        
        // Configure mock to return empty (product not in cart)
        when(cartDetailOutPort.findByCartIdAndProductId(cartId, productId)).thenReturn(Mono.empty());
        
        // Act & Assert
        StepVerifier.create(cartUseCase.removeProductFromCart(cartId, productId))
            .expectErrorMatches(throwable -> 
                throwable instanceof com.arka.microservice.sales_ms.domain.exception.NotFoundException && 
                throwable.getMessage().equals("Product not found in cart " + cartId))
            .verify();
        
        // Verify interactions
        verify(cartDetailOutPort).findByCartIdAndProductId(cartId, productId);
        verify(inventoryOutPort, never()).getProductInventory(any());
        verify(inventoryOutPort, never()).addStockToInventory(any(), any(), anyInt());
        verify(cartDetailOutPort, never()).delete(any());
    }

    @Test
    void showProductsInCart_Success() {
        // Arrange
        Long cartId = 1L;
        
        // Mock cart details
        com.arka.microservice.sales_ms.domain.model.CartDetailModel detail1 = 
            new com.arka.microservice.sales_ms.domain.model.CartDetailModel();
        detail1.setId(1L);
        detail1.setCartId(cartId);
        detail1.setProductId(101L);
        detail1.setQuantity(2);
        detail1.setSubtotal(20.0);
        
        com.arka.microservice.sales_ms.domain.model.CartDetailModel detail2 = 
            new com.arka.microservice.sales_ms.domain.model.CartDetailModel();
        detail2.setId(2L);
        detail2.setCartId(cartId);
        detail2.setProductId(102L);
        detail2.setQuantity(1);
        detail2.setSubtotal(15.0);
        
        // Configure mock
        when(cartDetailOutPort.findByCartId(cartId)).thenReturn(reactor.core.publisher.Flux.just(detail1, detail2));
        
        // Act & Assert
        StepVerifier.create(cartUseCase.showProductsInCart(cartId))
            .expectNext(detail1)
            .expectNext(detail2)
            .verifyComplete();
        
        // Verify interaction
        verify(cartDetailOutPort).findByCartId(cartId);
    }
    
    @Test
    void showProductsInCart_EmptyCart() {
        // Arrange
        Long cartId = 1L;
        
        // Configure mock to return empty flux (cart with no products)
        when(cartDetailOutPort.findByCartId(cartId)).thenReturn(reactor.core.publisher.Flux.empty());
        
        // Act & Assert
        StepVerifier.create(cartUseCase.showProductsInCart(cartId))
            .expectError(NotFoundException.class)
            .verify();
        
        // Verify interaction
        verify(cartDetailOutPort).findByCartId(cartId);
    }
}
