package com.example.restservice;
import java.math.BigDecimal;

public class DepositoDTO{
    private BigDecimal quantidade;

    public BigDecimal getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}