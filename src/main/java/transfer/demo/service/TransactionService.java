package transfer.demo.service;


import transfer.demo.models.entity.Transaction;

public interface TransactionService {
    void cashBoxDown (Transaction transaction);
    void cashBoxUp(Transaction transaction);
}
