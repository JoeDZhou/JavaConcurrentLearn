package com.z.part1.lesson5;

public class AccountWithBlock {
    public int balance;

    //转账
    void transfer(AccountWithBlock target, int amount) {
        //锁定转出账户
        synchronized (this) {
            //锁定转入账户
            synchronized (target) {
                if (this.balance > amount) {
                    this.balance -= amount;
                    target.balance += amount;
                }
            }
        }
    }

    //有可能导致相互等待形成死锁
}
