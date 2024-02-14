package com.product.domain;

import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Money {

    @JsonProperty
    private Currency currency;

    @JsonProperty
    private long value;

    private Money() {
        currency = null;
        value = 0;
    }

    private Money(Currency currency, long value) {
        this.currency = currency;
        this.value = value;
    }

    public static Money of(Currency currency, long value) {
        return new Money(currency, value);
    }

    public Currency getCurrency() {
        return currency;
    }

    public long getValue() {
        return value;
    }
}
