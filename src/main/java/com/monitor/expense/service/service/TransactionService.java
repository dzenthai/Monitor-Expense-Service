package com.monitor.expense.service.service;

import com.monitor.expense.service.dto.TransactionDTO;
import com.monitor.expense.service.entity.Transaction;
import com.monitor.expense.service.exception.TransactionException;
import com.monitor.expense.service.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public void saveTransaction(TransactionDTO transactionDTO) {

        log.info("Transaction Service | Saving transaction");

        validateTransaction(transactionDTO);

        Transaction transaction = convertToEntity(transactionDTO);

        transactionRepo.save(transaction);
    }

    private Transaction convertToEntity(TransactionDTO transactionDTO) {

        log.info("Transaction Service | Converting DTO to entity");

        return Transaction
                .builder()
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

        if (transactionDTO.accountFrom()==null) {
            throw new TransactionException("The account from cannot be null");
        }
        if (transactionDTO.accountTo()==null) {
            throw new TransactionException("The account to cannot be null");
        }
        if (transactionDTO.sum()==null) {
            throw new TransactionException("The sum cannot be null");
        }
        if (transactionDTO.currency()==null) {
            throw new TransactionException("The currency cannot be null");
        }
        if(transactionDTO.expenseCategory()==null) {
            throw new TransactionException("The expense category cannot be null");
        }
    }
}
