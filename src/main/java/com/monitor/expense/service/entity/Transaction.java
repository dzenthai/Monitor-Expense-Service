package com.monitor.expense.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "account_from")
    private String accountFrom;
    @Column(name = "account_to")
    private String accountTo;
    @Column(name = "sum", precision = 15, scale = 2)
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;
    @Column(name = "datetime")
    private LocalDateTime datetime;
}
