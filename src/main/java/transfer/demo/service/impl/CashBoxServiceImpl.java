package transfer.demo.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.demo.exception.ConnectionException;
import transfer.demo.models.entity.CashBox;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.repository.CashBoxRepository;
import transfer.demo.request.CashBoxRequest;
import transfer.demo.response.BaseResponse;
import transfer.demo.service.CashBoxService;
@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CashBoxServiceImpl implements CashBoxService {

    CashBoxRepository repository;

    @Override
    public BaseResponse save(CashBoxRequest request) {
        log.info("CashBoxServiceImpl -> {}", request);
        try {

        repository.save(CashBox.builder().name(request.getName()).build());
        return BaseResponse.builder()
                .message(ResponseMessage.CREATE_CASH_BOX.getMessage())
                .code(ResponseMessage.CREATE_CASH_BOX.getCode())
                .build();
        }catch (Exception e) {
            log.error("CashBoxServiceImpl -> {}", e.getLocalizedMessage());
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }

    }
}
