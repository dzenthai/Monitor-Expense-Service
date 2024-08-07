package com.monitor.expense.service.repository;

import com.monitor.expense.service.entity.ExpenseCategory;
import com.monitor.expense.service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByExpenseCategoryAndDatetimeBetween(ExpenseCategory expenseCategory, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
