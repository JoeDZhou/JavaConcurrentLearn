package com.z.part1.lesson5;

public class AccountSolveBlockWithOrder {
    private int id;
    private int balance;
    //转账
    void transfer(AccountSolveBlockWithOrder target, int amount) {
        AccountSolveBlockWithOrder left = this;
        AccountSolveBlockWithOrder right = target;
        if (this.id > target.id) {
            left = target;
            right = this;
        }
        //锁定序号小的账户
        synchronized (left) {
            //锁定序号大的账户
            synchronized (right) {
                if (this.balance > amount) {
                    this.balance -= amount;
                    target.balance += amount;
                }
            }
        }
    }
}
