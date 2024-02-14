package com.product.domain;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

import com.product.api.boudaries.ProductBoundry;
import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;

@Named
public class ProductInteractor implements ProductBoundry {

    private final ProductAction action;

    @Inject
    public ProductInteractor(ProductAction action) {
        this.action = action;
    }

    @Override
    public ProductResponse add(ProductRequest request) {
        return action.add(request);
    }

    @Override
    public ProductResponse get(String reference) {
        return action.get(reference);
    }

    @Override
    public List<ProductResponse> get() {
        return action.get();
    }

    @Override
    public void delete(String reference) {
        action.delete(reference);
    }

    @Override
    public ProductResponse update(String reference, ProductRequest request) {
        return action.update(reference, request);
    }
}
