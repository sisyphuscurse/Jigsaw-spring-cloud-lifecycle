package com.yiguan.core.transaction;

import org.springframework.transaction.support.TransactionSynchronization;

/**
 * Created by Barry on 11/18/15.
 */
public abstract class AbsTransactionSynchronizedSender implements TransactionSynchronization {
    @Override
    public void suspend() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void flush() {

    }

    @Override
    public void beforeCommit(boolean readOnly) {

    }

    @Override
    public void beforeCompletion() {

    }

    @Override
    public void afterCommit() {

    }

    @Override
    public void afterCompletion(int status) {

    }
}
