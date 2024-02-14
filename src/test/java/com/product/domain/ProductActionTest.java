package com.product.domain;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductActionTest {

    private ProductAction action;

    @BeforeEach
    public void setup() {
        Map<String, ProductRequest> productRequestsWithRef = new HashMap<>();
        productRequestsWithRef
            .put("ref1", request("Test-1", Money.of(Currency.getInstance("INR"), 100)));
        productRequestsWithRef
            .put("ref2", request("Test-2", Money.of(Currency.getInstance("INR"), 200)));
        action = new ProductAction(productRequestsWithRef);
    }

    @Test
    public void shouldAddProduct() {
        ProductResponse response = action
            .add(request("Test-3", Money.of(Currency.getInstance("INR"), 300)));

        assertNotNull(response);
        assertEquals("Test-3", response.getName());
        assertEquals("INR", response.getAmount().getCurrency().getCurrencyCode());
        assertEquals(300, response.getAmount().getValue());
    }

    @Test
    public void shouldGetProduct() {
        ProductResponse response = action.get("ref1");

        assertNotNull(response);
        assertEquals("Test-1", response.getName());
        assertEquals("INR", response.getAmount().getCurrency().getCurrencyCode());
        assertEquals(100, response.getAmount().getValue());
    }

    @Test
    public void shouldNotGetProductIfInvalidReference() {
        ProductResponse response = action.get("ref3");

        assertNull(response.getName());
        assertNull(response.getAmount());
    }

    @Test
    public void shouldGetAllProducts() {
        List<ProductResponse> response = action.get();

        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    public void shouldDeleteProduct() {
        action.delete("ref1");

        List<ProductResponse> response = action.get();
        assertEquals(1, response.size());
    }

    @Test
    public void shouldUpdateProduct() {
        ProductResponse response = action.update("ref1",
            request("Test-1", Money.of(Currency.getInstance("AED"), 300)));

        assertNotNull(response);
        assertEquals("Test-1", response.getName());
        assertEquals("AED", response.getAmount().getCurrency().getCurrencyCode());
        assertEquals(300, response.getAmount().getValue());
    }

    @Test
    public void shouldThrowExceptionWhenUpdateWithInvalidReference() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> action.update("ref3",
                request("Test-1", Money.of(Currency.getInstance("AED"), 300))));

        assertEquals("Invalid reference: ref3", exception.getMessage());
    }

    private ProductRequest request(String name, Money amount) {
        return ProductRequest.builder()
            .withName(name)
            .withAmount(amount)
            .build();
    }
}
