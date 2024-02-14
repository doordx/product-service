package com.product.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.domain.Money;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+")
    @JsonProperty
    private String name;

    @NotNull
    @JsonProperty
    private Money amount;

    public String getName() {
        return name;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private Money amount;

        private Builder() {
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        public ProductRequest build() {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setAmount(amount);
            productRequest.name = this.name;
            return productRequest;
        }
    }
}
