package transfer.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.demo.configuration.Auditable;
import transfer.demo.exception.ConnectionException;
import transfer.demo.exception.ServiceException;
import transfer.demo.models.entity.Transaction;
import transfer.demo.models.entity.User;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.models.enums.TransactionStatus;
import transfer.demo.repository.CashBoxRepository;
import transfer.demo.repository.TransactionRepository;
import transfer.demo.repository.UserRepository;
import transfer.demo.request.GlobalRequest;
import transfer.demo.request.PayerRequest;
import transfer.demo.request.ReceiverRequest;
import transfer.demo.request.TransactionRequest;
import transfer.demo.response.BaseResponse;
import transfer.demo.service.SendService;
import transfer.demo.service.TransactionService;

import java.time.LocalDateTime;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {

    UserRepository userRepository;
    CashBoxRepository cashBoxRepository;
    TransactionRepository transactionRepository;
    TransactionService service;

    @Auditable
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public BaseResponse sendPayment(GlobalRequest globalRequest) {

        log.info("SendServiceImpl -> {}", globalRequest);
        try {
            PayerRequest payerRequest = globalRequest.getPayerRequest();
            ReceiverRequest receiverRequest = globalRequest.getReceiverRequest();
            TransactionRequest transactionRequest = globalRequest.getTransactionRequest();
            saveUser(payerRequest, receiverRequest);

            Transaction transaction = Transaction.builder()
                    .status(TransactionStatus.valueOf(TransactionStatus.CREATE.name()))
                    .currency(transactionRequest.getCurrency())
                    .amount(transactionRequest.getAmount())
                    .comment(transactionRequest.getComment())
                    .sendCashBoxId(cashBoxRepository.findByName(transactionRequest.getSendCashBox()))
                    .receiveCashBoxId(cashBoxRepository.findByName(transactionRequest.getReceiveCashBox()))
                    .date(LocalDateTime.now())
                    .uniqueId(createUniqueCode())
                    .payerPhoneNumber(payerRequest.getPhoneNumber())
                    .receiverPhoneNumber(receiverRequest.getPhoneNumber())
                    .build();

            transactionRepository.save(transaction);
            service.cashBoxUp(transaction);

            return BaseResponse.builder()
                    .message(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage())
                    .code(String.valueOf(transaction.getUniqueId()))
                    .build();

        }catch (Exception e) {
            log.error("SendServiceImpl -> {}", e.getLocalizedMessage());
            throw new ServiceException(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
        }

    }
    @Auditable
    private int createUniqueCode () {

        try {
            int number = 0;
            boolean isCardExist = true;
            while (isCardExist) {
                number = (int) Math.ceil(Math.random() * (1000000 - 100000 + 1) + 100000);
                isCardExist = transactionRepository.existsTransactionByUniqueId(number);
            }
            return number;

        }catch (Exception e) {
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }
    }
    @Auditable
    @Transactional(Transactional.TxType.REQUIRED)
    protected void saveUser(PayerRequest payerRequest, ReceiverRequest receiverRequest) {

        try {
            userRepository.save(User.builder()
                .name(payerRequest.getName())
                .surname(payerRequest.getSurname())
                .lastname(payerRequest.getLastname())
                .phoneNumber(payerRequest.getPhoneNumber())
                .build());

            userRepository.save(User.builder()
                .name(receiverRequest.getName())
                .surname(receiverRequest.getSurname())
                .lastname(receiverRequest.getLastname())
                .phoneNumber(receiverRequest.getPhoneNumber())
                .build());
        }catch (Exception e) {
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }
    }
}
