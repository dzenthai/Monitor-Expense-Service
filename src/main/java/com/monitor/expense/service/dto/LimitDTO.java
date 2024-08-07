package com.monitor.expense.service.dto;

import com.monitor.expense.service.entity.ExpenseCategory;

import java.math.BigDecimal;

public record LimitDTO(ExpenseCategory category, BigDecimal limit, String month) {
}
