package com.monitor.expense.service.controller;

import com.monitor.expense.service.dto.TransactionDTO;
import com.monitor.expense.service.entity.Transaction;
import com.monitor.expense.service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.saveTransaction(transactionDTO);
        return ResponseEntity.ok().body("Transaction from %s, to %s, sum = %s%s successfully saved"
                .formatted(transactionDTO.accountFrom(), transactionDTO.accountTo(), transactionDTO.sum(), transactionDTO.currency()));
    }

    @QueryMapping
    public List<Transaction> transactions() {
        return transactionService.getAllTransactions();
    }
}
