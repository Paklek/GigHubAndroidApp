package com.gighub.app.model;

import java.util.List;

/**
 * Created by user on 07/11/2016.
 */
public class BankResponse extends Response {
    private List<Bank> bank;

    public List<Bank> getBank() {
        return bank;
    }

    public void setBank(List<Bank> bank) {
        this.bank = bank;
    }
}
