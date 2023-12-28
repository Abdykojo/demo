package transfer.demo.service;

import transfer.demo.request.CashBoxRequest;
import transfer.demo.response.BaseResponse;

public interface CashBoxService {
    BaseResponse save (CashBoxRequest request);
}
