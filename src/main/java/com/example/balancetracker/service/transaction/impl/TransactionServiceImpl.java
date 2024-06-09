package com.example.balancetracker.service.transaction.impl;

import com.example.balancetracker.domain.transaction.Transaction;
import com.example.balancetracker.repository.transaction.TransactionRepository;
import com.example.balancetracker.repository.user.UserRepository;
import com.example.balancetracker.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public long addMoney(long userId, int amount) {
        final var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        final var transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return transaction.getId();
    }
}