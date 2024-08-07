package com.monitor.expense.service.controller;

import com.monitor.expense.service.dto.LimitDTO;
import com.monitor.expense.service.dto.TransactionDTO;
import com.monitor.expense.service.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/limit")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService limitService;

    @PostMapping("/set")
    public ResponseEntity<String> setLimit(@RequestBody LimitDTO limitDTO) {
        limitService.setLimit(limitDTO);
        return ResponseEntity.ok("Limit set successfully.");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LimitDTO>> getAllLimits() {
        List<LimitDTO> limits = limitService.getAllLimits();
        return ResponseEntity.ok(limits);
    }

    @GetMapping("/exceededTransactions")
    public ResponseEntity<List<TransactionDTO>> getExceededTransactions() {
        List<TransactionDTO> transactions = limitService.getExceededTransactions();
        return ResponseEntity.ok(transactions);
    }
}
