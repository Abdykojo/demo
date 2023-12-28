package transfer.demo.service.impl;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.demo.configuration.Auditable;
import transfer.demo.models.entity.Transaction;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.models.enums.TransactionStatus;
import transfer.demo.repository.CashBoxRepository;
import transfer.demo.repository.TransactionRepository;
import transfer.demo.request.PaymentRequest;
import transfer.demo.response.BaseResponse;
import transfer.demo.service.ReceiveService;
import transfer.demo.service.TransactionService;


@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ReceiveServiceImpl implements ReceiveService {
    TransactionService service;
    TransactionRepository repository;
    CashBoxRepository cashBoxRepository;

    @Auditable
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public BaseResponse getPayment (PaymentRequest request) {
        log.info("ReceiveServiceImpl -> {}", request);

            Transaction transaction = repository.findByUniqueIdAndReceiverPhoneNumberAndReceiveCashBoxId
                    (request.getCode(), request.getPhoneNumber(), cashBoxRepository.findByName(request.getCashBoxName()));
            if (transaction == null) {
                return BaseResponse.builder()
                        .message(ResponseMessage.CODE_NOT_FOUND.getMessage())
                        .code(ResponseMessage.CODE_NOT_FOUND.getCode())
                        .build();
            }
        try {

            transaction.setStatus(TransactionStatus.ISSUED);
            repository.save(transaction);
            service.cashBoxDown(transaction);

            return BaseResponse.builder()
                    .code(ResponseMessage.TRANSACTION_ISSUED.getCode())
                    .message(ResponseMessage.TRANSACTION_ISSUED.getMessage()).build();

        }catch (PersistenceException e) {
            log.error("ReceiveServiceImpl -> {}", e.getLocalizedMessage());
            return BaseResponse.builder()
                    .message(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage())
                    .code(ResponseMessage.INTERNAL_SERVER_ERROR.getCode())
                    .build();
        }

    }

}
