package com.product.rest;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.api.ProductMediaType;
import com.product.api.boudaries.ProductBoundry;
import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;
import com.product.domain.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductBoundry productBoundry;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldAddProduct() throws Exception {
        ProductRequest request = ProductRequest.builder()
            .withAmount(Money.of(Currency.getInstance("INR"), 200))
            .withName("Test")
            .build();

        when(productBoundry.add(any(ProductRequest.class))).thenReturn(getResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(ProductMediaType.PRODUCT_MEDIA_TYPE_V1)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value("Test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.value")
                .value("200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.currency")
                .value("INR"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reference").isString());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        ProductRequest request = ProductRequest.builder()
            .withAmount(Money.of(Currency.getInstance("INR"), 200))
            .withName("Test")
            .build();

        when(productBoundry.update(any(String.class), any(ProductRequest.class))).thenReturn(getResponse());

        mockMvc.perform(MockMvcRequestBuilders.put("/products/10")
                .contentType(ProductMediaType.PRODUCT_MEDIA_TYPE_V1)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value("Test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.value")
                .value("200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.currency")
                .value("INR"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reference").isString());
    }

    @Test
    public void shouldGetProduct() throws Exception {
        when(productBoundry.get(any(String.class))).thenReturn(getResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/10")
                .contentType(ProductMediaType.PRODUCT_MEDIA_TYPE_V1))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value("Test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.value")
                .value("200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount.currency")
                .value("INR"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reference").isString());
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        when(productBoundry.get()).thenReturn(List.of(getResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(ProductMediaType.PRODUCT_MEDIA_TYPE_V1))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name")
                .value("Test"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount.value")
                .value("200"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount.currency")
                .value("INR"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].reference").isString());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/10")
                .contentType(ProductMediaType.PRODUCT_MEDIA_TYPE_V1))
            .andExpect(status().isNoContent());
    }

    private static ProductResponse getResponse() {
        ProductResponse response = ProductResponse.builder()
            .withReference(UUID.randomUUID().toString())
            .withName("Test")
            .withAmount(Money.of(Currency.getInstance("INR"), 200))
            .build();
        return response;
    }
}
