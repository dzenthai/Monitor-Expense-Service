package com.monitor.expense.service.dto;


import com.monitor.expense.service.entity.Currency;
import com.monitor.expense.service.entity.ExpenseCategory;

import java.math.BigDecimal;

public record TransactionDTO(String accountFrom, String accountTo, BigDecimal sum, Currency currency, ExpenseCategory expenseCategory) {
}
