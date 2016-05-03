package com.izhbg.typz.base.hibernate;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DelegatingTransactionDefinition;

public class ReadOnlyTransactionDefinition extends
        DelegatingTransactionDefinition {
    private static final long serialVersionUID = 0L;

    public ReadOnlyTransactionDefinition(
            TransactionDefinition transactionDefinition) {
        super(transactionDefinition);
    }

    public boolean isReadOnly() {
        return true;
    }
}
