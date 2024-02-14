package com.product.domain;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;

@Named
public class ProductAction {

    private Map<String, ProductRequest> products;

    @Inject
    public ProductAction() {
        products = new HashMap<>();
    }

    public ProductAction(Map<String, ProductRequest> product) {
        products = product;
    }

    public ProductResponse add(ProductRequest request) {
        String reference = UUID.randomUUID().toString();
        products.put(reference, request);
        return ProductResponse.builder()
            .withReference(reference)
            .withName(request.getName())
            .withAmount(request.getAmount())
            .build();
    }

    public ProductResponse get(String reference) {
        ProductRequest request = products.get(reference);
        if (isNull(request)) {
            return ProductResponse.builder().build();
        }
        return buildProductResponse(reference, request);
    }

    public List<ProductResponse> get() {
        return products.entrySet().stream().map(ProductAction::buildProductResponse).collect(Collectors.toList());
    }

    public void delete(String reference) {
        products.remove(reference);
    }

    public ProductResponse update(String reference, ProductRequest request) {
        ProductRequest product = products.get(reference);
        if (isNull(product)) {
            throw new IllegalArgumentException("Invalid reference: " + reference);
        }
        product.setAmount(request.getAmount());
        return buildProductResponse(reference, product);
    }

    private static ProductResponse buildProductResponse(String reference, ProductRequest product) {
        return ProductResponse.builder()
            .withReference(reference)
            .withAmount(product.getAmount())
            .withName(product.getName())
            .build();
    }

    private static ProductResponse buildProductResponse(Map.Entry<String, ProductRequest> productRequestByRef) {
        return ProductResponse.builder()
            .withName(productRequestByRef.getValue().getName())
            .withAmount(productRequestByRef.getValue().getAmount())
            .withReference(productRequestByRef.getKey())
            .build();
    }
}
