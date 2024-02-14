package com.product.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.domain.Money;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {

    @JsonProperty
    private String reference;

    @JsonProperty
    private String name;

    @JsonProperty
    private Money amount;

    public String getName() {
        return name;
    }

    public Money getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String reference;
        private String name;
        private Money amount;

        private Builder() {
        }

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withAmount(Money amount) {
            this.amount = amount;
            return this;
        }

        public ProductResponse build() {
            ProductResponse productResponse = new ProductResponse();
            productResponse.reference = this.reference;
            productResponse.amount = this.amount;
            productResponse.name = this.name;
            return productResponse;
        }
    }
}
