package com.monitor.expense.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "limits")
@NoArgsConstructor
@AllArgsConstructor
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ExpenseCategory category;

    @Column(name = "limit_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal limitAmount;

    @Column(name = "month", nullable = false)
    private String month;
}
