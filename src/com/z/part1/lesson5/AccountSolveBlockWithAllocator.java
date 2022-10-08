package com.z.part1.lesson5;

import java.util.ArrayList;
import java.util.List;

public class AccountSolveBlockWithAllocator {
    class Allocator {
        private List<Object> als = new ArrayList<>();

        //一次性申请所有资源
        synchronized boolean apply(Object from, Object to) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }

            return true;
        }

        //归还资源
        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
        }
    }

    class Account {
        private Allocator allocator;
        private int balance;

        //转账
        void transfer(Account target, int amount) {
            //一次性申请转出账户和转入账户，直到成功
            while (!allocator.apply(this, target));

            try {
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
            } finally {
                allocator.free(this, target);
            }
        }
    }
}
