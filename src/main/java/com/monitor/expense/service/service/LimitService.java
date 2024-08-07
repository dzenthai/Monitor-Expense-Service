package com.monitor.expense.service.service;

import com.monitor.expense.service.dto.LimitDTO;
import com.monitor.expense.service.dto.TransactionDTO;
import com.monitor.expense.service.entity.Currency;
import com.monitor.expense.service.entity.Limit;
import com.monitor.expense.service.entity.Transaction;
import com.monitor.expense.service.repository.LimitRepo;
import com.monitor.expense.service.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LimitService {

    private static final BigDecimal DEFAULT_LIMIT = BigDecimal.valueOf(1000);
    private final LimitRepo limitRepo;
    private final TransactionRepo transactionRepo;

    public void setLimit(LimitDTO limitDTO) {
        Optional<Limit> existingLimit = limitRepo.findByCategoryAndMonth(limitDTO.category(), limitDTO.month());
        Limit limit;
        if (existingLimit.isPresent()) {
            limit = existingLimit.get();
            limit.setLimitAmount(limitDTO.limit());
        } else {
            limit = Limit.builder()
                    .category(limitDTO.category())
                    .limitAmount(limitDTO.limit())
                    .month(limitDTO.month())
                    .build();
        }
        limitRepo.save(limit);
    }


    public List<LimitDTO> getAllLimits() {
        return limitRepo.findAll().stream().map(limit -> new LimitDTO(
                limit.getCategory(),
                limit.getLimitAmount(),
                limit.getMonth()
        )).collect(Collectors.toList());
    }

    public List<TransactionDTO> getExceededTransactions() {
        List<Transaction> transactions = transactionRepo.findAll();
        String currentMonth = YearMonth.now().toString();

        return transactions.stream()
                .filter(transaction -> transaction.getCurrency() == Currency.USD)
                .filter(transaction -> isExceeded(transaction, currentMonth))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isExceeded(Transaction transaction, String currentMonth) {
        Limit limit = limitRepo.findByCategoryAndMonth(transaction.getExpenseCategory(), currentMonth)
                .orElse(new Limit(null, transaction.getExpenseCategory(), DEFAULT_LIMIT, currentMonth));
        return transaction.getSum().compareTo(limit.getLimitAmount()) > 0;
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getAccountFrom(),
                transaction.getAccountTo(),
                transaction.getSum(),
                transaction.getCurrency(),
                transaction.getExpenseCategory()
        );
    }
}
