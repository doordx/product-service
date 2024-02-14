package com.product.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import java.util.List;

import com.product.api.ProductMediaType;
import com.product.api.request.ProductRequest;
import com.product.api.response.ProductResponse;
import com.product.api.boudaries.ProductBoundry;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products")
@Produces({ProductMediaType.PRODUCT_MEDIA_TYPE_V1})
@Consumes({ProductMediaType.PRODUCT_MEDIA_TYPE_V1})
public class ProductController {

    private ProductBoundry productboundry;

    @Inject
    public ProductController(ProductBoundry productService) {
        this.productboundry = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse add(@Valid @RequestBody ProductRequest request) {
        return productboundry.add(request);
    }

    @GetMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse get(@PathVariable String reference) {
        return productboundry.get(reference);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> get() {
        return productboundry.get();
    }

    @DeleteMapping("/{reference}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String reference) {
        productboundry.delete(reference);
    }

    @PutMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@Valid @PathVariable String reference, @RequestBody ProductRequest request) {
        return productboundry.update(reference, request);
    }
}
