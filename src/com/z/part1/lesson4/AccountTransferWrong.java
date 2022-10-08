package com.z.part1.lesson4;

public class AccountTransferWrong {
    private int balance;

    //转账
    synchronized void transfer(AccountTransferWrong target, int amount) {
        if (this.balance > amount) {
            this.balance -= amount;
            target.balance += amount;
        }
    }
}
