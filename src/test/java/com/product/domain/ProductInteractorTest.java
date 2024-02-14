package com.product.domain;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductInteractorTest {

    @Mock
    private ProductAction action;

    @InjectMocks
    private ProductInteractor interactor;

    @Test
    public void shouldAddProduct() {
        when(action.add(any(ProductRequest.class))).thenReturn(ProductResponse.builder().build());

        interactor.add(ProductRequest.builder().build());

        verify(action, times(1)).add(any(ProductRequest.class));
    }

    @Test
    public void shouldUpdateProduct() {
        when(action.update(any(String.class), any(ProductRequest.class))).thenReturn(ProductResponse.builder().build());

        interactor.update(UUID.randomUUID().toString(), ProductRequest.builder().build());

        verify(action, times(1)).update(any(String.class), any(ProductRequest.class));
    }

    @Test
    public void shouldGetProduct() {
        when(action.get(any(String.class))).thenReturn(ProductResponse.builder().build());

        interactor.get(UUID.randomUUID().toString());

        verify(action, times(1)).get(any(String.class));
    }

    @Test
    public void shouldGetAllProducts() {
        when(action.get()).thenReturn(List.of(ProductResponse.builder().build()));

        interactor.get();

        verify(action, times(1)).get();
    }

    @Test
    public void shouldDeleteProduct() {
        doNothing().when(action).delete(any(String.class));

        interactor.delete(UUID.randomUUID().toString());

        verify(action, times(1)).delete(any(String.class));
    }
}
