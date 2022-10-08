package com.z.part1.lesson4;

public class AccountBase {
    //余额锁
    private final Object balLock = new Object();
    //密码锁
    private final Object pwLock = new Object();

    //账户余额
    private Integer balance;
    //账户密码
    private String password;

    //取款
    void withdraw(int amount) {
        synchronized (balLock) {
            if (this.balance > amount) {
                this.balance -= amount;
            }
        }
    }
    //查看余额
    Integer getBalance() {
        synchronized (balLock) {
            return balance;
        }
    }

    //更改密码
    void updatePassword(String newPassword) {
        synchronized (pwLock) {
            this.password = newPassword;
        }
    }

    //查看密码
    String getPassword() {
        synchronized (pwLock) {
            return password;
        }
    }
}
