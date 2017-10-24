package com.yiguan.core.transaction;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Barry on 11/18/15.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransactionSynchronizedExecutor extends AbsTransactionSynchronizedSender {

    private List<Runnable> afterCommitTasks = Lists.newLinkedList();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void registerRunnableAfterCommit(Runnable r) {
        afterCommitTasks.add(r);
        if(TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(this);
        } else {
            perform();
        }
    }

    @Override
    public void afterCommit() {
        perform();
    }

    private void perform() {
        try {
            for (final Runnable runnable : afterCommitTasks) {
                executorService.submit(runnable);
            }
        } finally {
            afterCommitTasks.clear();
        }
    }
}
