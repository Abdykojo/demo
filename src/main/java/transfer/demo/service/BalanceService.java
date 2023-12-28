package transfer.demo.service;

import transfer.demo.request.BalanceRequest;
import transfer.demo.response.BaseResponse;

public interface BalanceService {
    BaseResponse createBalance (BalanceRequest request);
}
