package transfer.demo.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import transfer.demo.exception.ConnectionException;
import transfer.demo.models.entity.Balance;
import transfer.demo.models.enums.ResponseMessage;
import transfer.demo.repository.BalanceRepository;
import transfer.demo.request.BalanceRequest;
import transfer.demo.response.BaseResponse;
import transfer.demo.service.BalanceService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    BalanceRepository balanceRepository;

    @Override
    public BaseResponse createBalance(BalanceRequest request) {
        log.info("BalanceServiceImpl -> {}", request);
        try {

            balanceRepository.save(Balance.builder()
                    .balance(request.getBalance())
                    .cashBoxId(request.getCashBoxId())
                    .currency(request.getCurrency()).build());
            return BaseResponse.builder()
                    .code(ResponseMessage.CREATE_BALANCE.getCode())
                    .message(ResponseMessage.CREATE_BALANCE.getMessage()).build();
        }catch (Exception e) {
            log.error("BalanceServiceImpl -> {}", e.getLocalizedMessage());
            throw new ConnectionException(ResponseMessage.CONNECTION_REFUSED.getMessage());
        }
    }

}
