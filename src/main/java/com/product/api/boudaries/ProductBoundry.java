package com.product.api.boudaries;

import java.util.List;

import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;

public interface ProductBoundry {

    ProductResponse add(ProductRequest request);

    ProductResponse get(String reference);

    List<ProductResponse> get();

    void delete(String reference);

    ProductResponse update(String reference, ProductRequest request);
}
