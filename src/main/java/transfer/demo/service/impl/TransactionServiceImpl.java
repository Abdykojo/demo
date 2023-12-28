package transfer.demo.service.impl;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import transfer.demo.configuration.Auditable;
import transfer.demo.exception.BalanceException;
import transfer.demo.exception.ConnectionException;
import transfer.demo.models.entity.Balance;
import transfer.demo.models.entity.Transaction;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.repository.BalanceRepository;
import transfer.demo.service.TransactionService;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransactionServiceImpl implements TransactionService {

    BalanceRepository balanceRepository;

    public TransactionServiceImpl(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }
//    Counter counter;
//
//    public  TransactionServiceImpl (BalanceRepository balanceRepository, MeterRegistry meterRegistry) {
//        this.balanceRepository = balanceRepository;
//        AtomicInteger atomicInteger = new AtomicInteger();
//        meterRegistry.gauge("custom_gauge", atomicInteger);
//        counter = meterRegistry.counter("custom_counter");
//
//    }

    @Override
    @Auditable
    @Transactional(Transactional.TxType.REQUIRED)
    public void cashBoxUp(Transaction transaction) {

        try {
        Balance balance = balanceRepository.findByCashBoxIdAndCurrency(transaction.getSendCashBoxId(),transaction.getCurrency());
        balance.setBalance(balance.getBalance() + transaction.getAmount());
        if (balance.getBalance()<0) {
            throw new BalanceException(ResponseMessage.BALANCE_IS_NULL.getMessage());
        }
        balanceRepository.save(balance);
//        counter.increment();

        }catch (Exception e) {
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }
    }

    @Override
    @Auditable
    @Transactional(Transactional.TxType.REQUIRED)
    public void cashBoxDown(Transaction transaction) {

        try {
            Balance balance = balanceRepository.findByCashBoxIdAndCurrency(transaction.getReceiveCashBoxId(),transaction.getCurrency());
            balance.setBalance(balance.getBalance() - transaction.getAmount());
            balanceRepository.save(balance);
//            counter.increment();
        }catch (Exception e) {
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }
    }
}
