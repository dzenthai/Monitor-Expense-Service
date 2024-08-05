package com.monitor.expense.service.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
