package com.example.restservice;
import java.math.BigDecimal;

public class SaqueDTO{
    private BigDecimal quantidade;

    public BigDecimal getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}