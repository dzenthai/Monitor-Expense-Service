package com.monitor.expense.service.repository;

import com.monitor.expense.service.entity.ExpenseCategory;
import com.monitor.expense.service.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LimitRepo extends JpaRepository<Limit, Long> {
    Optional<Limit> findByCategoryAndMonth(ExpenseCategory category, String month);
}
