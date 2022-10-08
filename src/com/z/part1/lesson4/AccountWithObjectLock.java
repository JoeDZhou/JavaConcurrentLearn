package com.z.part1.lesson4;

public class AccountWithObjectLock {
    public int balance;

    //转账
    void  transfer(AccountWithObjectLock target, int amount) {
        synchronized (AccountWithObjectLock.class) {
            if (this.balance > amount) {
                this.balance -= amount;
                target.balance += amount;
            }
        }
    }
}
