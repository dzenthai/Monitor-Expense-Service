package com.monitor.expense.service.service;

import com.monitor.expense.service.dto.LimitDTO;
import com.monitor.expense.service.dto.TransactionDTO;
import com.monitor.expense.service.entity.*;
import com.monitor.expense.service.exception.TransactionException;
import com.monitor.expense.service.repository.LimitRepo;
import com.monitor.expense.service.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private static final BigDecimal DEFAULT_LIMIT = BigDecimal.valueOf(1000);

    private final TransactionRepo transactionRepo;
    private final LimitRepo limitRepo;

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public void saveTransaction(TransactionDTO transactionDTO) {
        log.info("Transaction Service | Saving transaction");

        validateTransaction(transactionDTO);
        checkLimitExceeded(transactionDTO);

        Transaction transaction = convertToEntity(transactionDTO);
        transactionRepo.save(transaction);
    }

    private Transaction convertToEntity(TransactionDTO transactionDTO) {
        log.info("Transaction Service | Converting DTO to entity");

        return Transaction.builder()
                .accountFrom(transactionDTO.accountFrom())
                .accountTo(transactionDTO.accountTo())
                .sum(transactionDTO.sum())
                .currency(transactionDTO.currency())
                .expenseCategory(transactionDTO.expenseCategory())
                .datetime(LocalDateTime.now())
                .build();
    }

    private void validateTransaction(TransactionDTO transactionDTO) {
        log.info("Transaction Service | Validating transaction");

        if (transactionDTO.accountFrom() == null) {
            throw new TransactionException("The account from cannot be null");
        }
        if (transactionDTO.accountTo() == null) {
            throw new TransactionException("The account to cannot be null");
        }
        if (transactionDTO.sum() == null) {
            throw new TransactionException("The sum cannot be null");
        }
        if (transactionDTO.currency() == null) {
            throw new TransactionException("The currency cannot be null");
        }
        if (transactionDTO.expenseCategory() == null) {
            throw new TransactionException("The expense category cannot be null");
        }
    }

    private void checkLimitExceeded(TransactionDTO transactionDTO) {
        log.info("Transaction Service | Checking if transaction exceeds limit");

        if (transactionDTO.currency() == Currency.USD) {
            String currentMonth = YearMonth.now().toString();
            Optional<Limit> optionalLimit = limitRepo.findByCategoryAndMonth(transactionDTO.expenseCategory(), currentMonth);
            BigDecimal limitAmount = optionalLimit.map(Limit::getLimitAmount).orElse(DEFAULT_LIMIT);

            BigDecimal totalTransactionsAmount = transactionRepo.findAllByExpenseCategoryAndDatetimeBetween(
                            transactionDTO.expenseCategory(),
                            YearMonth.now().atDay(1).atStartOfDay(),
                            YearMonth.now().atEndOfMonth().atTime(23, 59, 59))
                    .stream()
                    .map(Transaction::getSum)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalTransactionsAmount.add(transactionDTO.sum()).compareTo(limitAmount) > 0) {
                throw new TransactionException("Transaction exceeds the limit for the month");
            }
        }
    }
}
